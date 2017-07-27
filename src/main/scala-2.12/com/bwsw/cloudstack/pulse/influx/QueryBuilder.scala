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

object FieldExpr {
  def apply() = new FieldExpr
}
class FieldExpr extends Expr

object WhereExpr {
  def apply() = new WhereExpr
}
class WhereExpr extends Expr {

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
    this
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

  private val whereExpression: WhereExpr = new WhereExpr
  def where: WhereExpr = {
    whereExpression
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

}