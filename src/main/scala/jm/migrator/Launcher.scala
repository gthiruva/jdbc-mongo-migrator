package jm.migrator

import migration.SQLImporter
import parser.MappingParser
import db.MongoUtil

import net.lag.configgy.Configgy
import net.lag.logging.Logger




/**
 * Authod: Yuri Buyanov
 * Date: 2/3/11 1:13 PM
 */

object Launcher {
  Configgy.configure("jmm.conf")
  val config = Configgy.config
  val log = Logger.get
  val parser = new MappingParser()

  def main(args: Array[String]) = {
    log.debug("Using config: ")
    log.debug(config.toConfigString)
    val filename = args.headOption.getOrElse(config.getString("mapping.file", "./data/mapping.json"))
    val collections = parser.parseFile(filename)
    log.debug("Collections: "+collections)
    log.debug(collections toString)
    val importer = new SQLImporter(collections)
    importer.fetch// foreach (seq => log.debug(seq.toString))
    MongoUtil.close
    ()
  }
}