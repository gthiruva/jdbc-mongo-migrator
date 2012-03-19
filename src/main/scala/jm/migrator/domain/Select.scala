package jm.migrator.domain

import jm.migrator.util.Implicits._
import scala.collection.mutable.StringBuilder

trait Select {
  def mapping: Selectable
  def from: String
  def where: String = ""

  def toSQL(expressionParams: Map[String, Any] = Map.empty) = {
    val builder = new StringBuilder
    builder ++="SELECT "
    builder ++= mapping.columnsString
    builder ++= " FROM " ++ from
    if (where.length > 0) {
      builder ++= " WHERE " ++ where.render(expressionParams.mapValues(_.toString))
    }
    builder toString
  }
}