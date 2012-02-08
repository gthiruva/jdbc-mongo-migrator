package jm.migrator.db

import com.twitter.logging._
import com.mongodb.casbah._
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.WriteConcern
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject
import org.bson.types.ObjectId

import _root_.jm.migrator._

object MongoUtil {
  val log = Logger.get(getClass)
  log.setLevel(Level.DEBUG)
  log.addHandler(new ConsoleHandler(new Formatter(), None))

  val dryRun = Launcher.settings.mongoDryRun

  val (connOpt, dbOpt) =
    if (dryRun) {
      log.warning("DRY RUN, NO ACTUAL DATA WRITTEN")
      (None, None)
    } else {
      val conn = MongoConnection(Launcher.settings.mongoHost, Launcher.settings.mongoPort)
      log.info("Connected to Mongo DB host %s at %s", Launcher.settings.mongoHost, conn.debugString)

      val db = conn(Launcher.settings.mongoDatabase)
      log.info("Using DB %s", db)

      val user = "" //  config.getString("mongo.user", "")
      if (user.length > 0) {
        log.info("Authenticating as %s", user)
        db.authenticate(user, "") // config.getString("mongo.password", ""))
      }

      if (Launcher.settings.mongoClean) {
        log.info("mongo.clean = true, dropping DB before migration")
        db.dropDatabase
      }
      else {
        log.info("mongo.clean = false => NOT Dropping DB")
      }

      (Some(conn), Some(db))
    }

  def expandPair(k: String, v: Any): Pair[String, Any] = {
    val names = k.split('.')
    val value = names.tail match {
      case empty if empty.isEmpty => v

      case arr => {
        val innermost: Any = MongoDBObject(arr.last -> v)
        arr.init.foldLeft(innermost) { (value, name) =>
          (name -> value)
        }
      }
    }
    print(names.head, value)
    (names.head, value)
  }

  //unflattens the map according to dot notation
  def clusterByPrefix(fields: Iterable[(String, Any)]): Iterable[(String, Any)] = {
    val result = fields.groupBy( _._1.split('.').head) map { case (k, v) =>
      val firstKey = v.head._1
      val newValue =
        if (firstKey.contains('.')) {
          MongoDBObject(clusterByPrefix(v map { p => (p._1 drop p._1.indexOf('.')+1, p._2)}).toList)
        } else {
          v.head._2
        }
      (k, newValue)
    }

    result
  }

  import scala.collection.mutable.Map
  val idCache = Map[(String, Any), ObjectId]()

  def getMongoId(value: Any, collection: String): ObjectId =
    idCache.get(collection, value)
      .map{ id =>
        log.debug("Cache hit for $oid %s in \"%s\"", id.toString, collection)
        id
      }.getOrElse{
        val id = new ObjectId()
        idCache.put((collection, value), id)
        log.debug("Cache miss: created $oid %s in \"%s\"", id.toString, collection)
        id
      }

  def doInsert(objects: Seq[DBObject], collectionName: String) = {
    log.info("Inserting %d entries into \"%s\" collection", objects.size, collectionName)
    objects foreach (o => log.trace("%s %s", o.toMap.get("premium_amount").toString, o.toMap.get("premium_amount").getClass))
    dbOpt.foreach { db =>
      log.trace("Accessing collection: " + collectionName)
      val collection = db.getCollection(collectionName)
      log.trace("Inserting " + objects.length + " into collection ...")
      log.debug("WriteConcern: " + WriteConcern.Safe)
      val result = collection.insert(objects.toArray, WriteConcern.Safe)
      log.trace("Insert result: %s", result)
    }
  }

  def close = {
    connOpt foreach { conn =>
      log.info("Closing connection %s", conn)
      conn.close
    }
  }
}

trait InsertBackend {
  def doInsert: (Seq[DBObject], String) => Unit
}

trait MongoInsertBackend extends InsertBackend {
  def doInsert = MongoUtil.doInsert
}