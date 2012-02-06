package jm.migrator.domain

import jm.migrator.util.Implicits._

trait Select {
  def mapping: Selectable
  def from: String
  def where: String = ""

  def toSQL(expressionParams: Map[String, Any] = Map.empty) = {
    val builder = new StringBuilder("SELECT ")
    builder ++= mapping.columnsString
    builder ++= " FROM " ++ from
    if (where.length > 0) {
      builder ++= " WHERE " ++ where.render(expressionParams.mapValues(_.toString))
    }
    builder toString
  }
}