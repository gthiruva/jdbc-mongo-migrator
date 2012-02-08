package jm.migrator.db

import java.sql.{Connection, DriverManager, ResultSet}
import com.twitter.ostrich.admin._
import com.twitter.ostrich.admin.config._
import com.twitter.logging._
import _root_.jm.migrator._

object DBUtil {
  private val sett = Launcher.settings;
  private val log = Logger.get(getClass)
  log.setLevel(Level.DEBUG)
  log.addHandler(new ConsoleHandler(new Formatter(), None))

  Class.forName(sett.jdbcDriver).newInstance;
  val connection = DriverManager getConnection (sett.jdbcUri)

  log.debug("Using driver " + sett.jdbcDriver + " to reach " + sett.jdbcUri)
  
  def using[Closeable <: {def close(): Unit}, B](closeable: Closeable)(getB: Closeable => B): B =
    try {
      getB(closeable)
    } finally {
      closeable.close()
    }
}