package com.bwsw.cloudstack.pulse.influx

import scala.collection._

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */

object QueryBuilder {
  def apply() = new QueryBuilder
}

class QueryBuilder {
  def select: Select = new Select
}

class Expr {
  override def toString: String = super.toString
}

class WhereExpr(select: Select) extends Expr {

  private case class QuotedValue(value: String) {
    override def toString(): String = {
      "'" + value + "'"
    }
  }

  private case class QuotedField(fieldName: String) {
    override def toString(): String = s""""$fieldName""""
  }

  private var _aggregation: String = _
  private var _range: String = _
  private var _shift: String = _
  def timeSpan(aggregation: String, range: String, shift: String): WhereExpr = {
    _aggregation = aggregation
    _range = range
    _shift = shift
    this
  }

  private val andEqExpressions = mutable.Map[String, String]()
  def andEq(field: String, expr: String): WhereExpr = {
    andEqExpressions(field) = expr
    this
  }

  def groupByAggregation = {
    select
  }

  def build = {
    "WHERE " +
      andEqExpressions.map(kv => QuotedField(kv._1) + " = " + QuotedValue(kv._2)).mkString(" AND ") +
      " AND time > now() - " + _range + " - " + _shift + " AND time < now() - " + _shift +
      " GROUP BY time(" + _aggregation + ")"
  }

}

class Select extends Expr {
  private val fieldExpressions = mutable.Map[String, String]()
  def field(asName: String, formula: String): Select = {
    fieldExpressions(asName) = formula
    this
  }

  private var tableExpression: String = _
  def from(table: String): Select = {
    tableExpression = table
    this
  }

  private val whereExpression: WhereExpr = new WhereExpr(this)
  def where: WhereExpr = {
    whereExpression
  }

  def build: String = {
    "SELECT " +
    fieldExpressions.map(kv => kv._2 + " AS " + kv._1).mkString(", ") +
    s""" FROM "$tableExpression" """ +
    where.build
  }
}

object Test {
  var q = QueryBuilder()
    .select
      .field("cpuTime","")
      .from("cputime")
      .where
      .timeSpan("1d","1h","0h")
      .andEq("vmUuid","")
      .groupByAggregation
      .build

}