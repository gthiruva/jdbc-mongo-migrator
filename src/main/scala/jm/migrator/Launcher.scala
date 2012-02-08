package jm.migrator

import db.{MongoInsertBackend, MongoUtil}
import migration.SQLImporter
import parser.MappingParser
import com.twitter.ostrich.admin._
import com.twitter.ostrich.admin.config._
import com.twitter.logging._

class MigrationSettings(val mappingFileName: String,
val mappingShortUrlAlphabet : String,
val mappingShortUrlBlockSize : Int,
val mappingShortUrlMinLength : Int,

val jdbcDriver : String,
val jdbcUri : String,
val jdbcLimit : Int,

val mongoDryRun : Boolean,
val mongoHost : String,
val mongoPort : Int,
val mongoDatabase : String,
val mongoClean : Boolean,

val logFilename : String,
val logLevel : String,
val logUTC : Boolean,
val logConsole : Boolean
)

class MongoImportConfig extends com.twitter.util.Config[MigrationSettings] {
  var mappingFileName = required[String]
  var mappingShortUrlAlphabet = required[String]
  var mappingShortUrlBlockSize = required[Int]
  var mappingShortUrlMinLength = required[Int]

  var jdbcDriver = required[String]
  var jdbcUri = required[String]
  var jdbcLimit = required[Int]

  var mongoDryRun = required[Boolean]
  var mongoHost = required[String]
  var mongoPort= required[Int]
  var mongoDatabase = required[String]
  var mongoClean = required[Boolean]

  var logFilename = required[String]
  var logLevel = required[String]
  var logUTC = required[Boolean]
  var logConsole = required[Boolean]

  def getFoo() = { 55 }

def get_mappingFileName() = { mappingFileName }
def get_mappingShortUrlAlphabet = mappingShortUrlAlphabet
def get_mappingShortUrlBlockSize = mappingShortUrlBlockSize
def get_mappingShortUrlMinLength = mappingShortUrlMinLength

def get_jdbcDriver = jdbcDriver
def get_jdbcUri = jdbcUri
def get_jdbcLimit = jdbcLimit

def get_mongoDryRun = mongoDryRun
def get_mongoHost = mongoHost
def get_mongoPort = mongoPort
def get_mongoDatabase = mongoDatabase
def get_mongoClean = mongoClean

def get_logFilename = logFilename
def get_logLevel = logLevel
def get_logUTC = logUTC
def get_logConsole = logConsole


  def apply() = {
    val sett = new MigrationSettings(
	mappingFileName,
	mappingShortUrlAlphabet,
	mappingShortUrlBlockSize,
	mappingShortUrlMinLength,
	jdbcDriver,
	jdbcUri,
	jdbcLimit,
	mongoDryRun,
	mongoHost,
	mongoPort,
	mongoDatabase,
	mongoClean,
	logFilename,
	logLevel,
	logUTC,
	logConsole
	)
	sett
 }
}

object Launcher {
  val log = Logger.get(getClass)
  log.setLevel(Level.DEBUG)
  log.addHandler(new ConsoleHandler(new Formatter(), None))
  val parser = new MappingParser()
  val configFile = new java.io.File("MongoImportConfig.scala")
  val eval = new com.twitter.util.Eval
  val config = eval[com.twitter.util.Config[MigrationSettings]](configFile)
  config.validate()
  val settings = config()
    
  def main(args: Array[String]) = {
	val runtime = RuntimeEnvironment(this, args)
    log.debug("Loading config mapping from: " + settings.mappingFileName)

    val collections = parser.parseFile(settings.mappingFileName)
    log.debug("Collections: "+collections)
    log.trace(collections toString)
    val importer = new SQLImporter(collections) with MongoInsertBackend
    importer.fetch   // .foreach (seq => log.debug(seq.toString))
    MongoUtil.close
    // ()
  }
}