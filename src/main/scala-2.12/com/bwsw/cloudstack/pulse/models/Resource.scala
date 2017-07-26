package com.bwsw.cloudstack.pulse.models

import com.bwsw.cloudstack.pulse.influx.InfluxUtil
import org.influxdb.dto.QueryResult

object Resource {
  def transformAggregationToSeconds(value: String) = {
    val Pattern = "([0-9]+)([mhd])".r
    value match {
      case Pattern(number, scale) => scale match {
        case "d" => number.toInt * 3600 * 24
        case "h" => number.toInt * 3600
        case "m" => number.toInt * 60
      }
    }
  }
}

case class CounterField(name: String, params: Map[String, String]) {
  override def toString() = {
    s"""NON_NEGATIVE_DERIVATIVE(MEAN("$name"), """ + params("aggregation") + ") / "  + Resource.transformAggregationToSeconds(params("aggregation")) + s""" AS "$name""""
  }
}

case class GaugeField(name: String) {
  override def toString(): String = {
    s"""MEAN("$name") AS "$name""""
  }
}

case class QuotedValue(value: String) {
  override def toString(): String = {
    "'" + value + "'"
  }
}

case class QuotedField(fieldName: String) {
  override def toString(): String = s""""$fieldName""""
}

abstract class Resource {
  def getResult(params: Map[String, String]): QueryResult = {
    val query: String = prepareQuery(params)
    InfluxUtil.executeQuery(query)
  }

  def timeSpec(params: Map[String, String]): String = {
    " AND " +
      "time > now() - " + params("range") + " - " + params("shift") + " AND " +
      "time < now() - " + params("shift") + " GROUP BY time(" + params("aggregation") + ")"
  }

  def select: String = "SELECT "
  def where: String = "WHERE "
  def and: String = " AND "
  def eq: String = " = "
  def from(table: String): String = s""" FROM "$table" """

  def prepareQuery(params: Map[String, String]): String


}


class Cpu extends Resource {
  override def prepareQuery(params: Map[String, String]): String = {
    val whereParams = "WHERE " +
      QuotedField("vmUuid") + eq + QuotedValue(params("uuid"))

    select + """NON_NEGATIVE_DERIVATIVE(MEAN("cpuTime"), """ + params("aggregation") + ") / " +
      """LAST("cpus") / """ + Resource.transformAggregationToSeconds(params("aggregation")) + """ * 100 AS "cpuTime"""" + from("cpuTime") + whereParams + timeSpec(params)
  }
}

class Ram extends Resource {
  override def prepareQuery(params: Map[String, String]): String = {
    val whereParams =
      QuotedField("vmUuid") + eq + QuotedValue(params("uuid"))

    select +
      GaugeField("rss") +
      from("rss") +
      where +
      whereParams + timeSpec(params)
  }
}

class Disk extends Resource {
  val counters = List("ioErrors", "readBytes", "writeBytes", "readIOPS", "writeIOPS")
  override def prepareQuery(params: Map[String, String]): String = {

    val whereParams =
      QuotedField("vmUuid") + eq + QuotedValue(params("uuid")) + and +
      QuotedField("image") + eq + QuotedValue(params("diskUuid"))

    select +
      counters.map(metric => CounterField(metric, params)).mkString(", ") +
      from("disk") +
      where +
      whereParams + timeSpec(params)
  }
}


class Network extends Resource {
  val counters = List("readBytes", "writeBytes", "readErrors", "writeErrors", "readDrops", "writeDrops", "readPackets", "writePackets")
  override def prepareQuery(params: Map[String, String]): String = {

    val whereParams =
      QuotedField("vmUuid") + eq + QuotedValue(params("uuid")) + and +
      QuotedField("mac") + eq + QuotedValue(params("mac"))

    select +
      counters.map(metric => CounterField(metric, params)).mkString(", ") +
      from("networkInterface") +
      where +
      whereParams + timeSpec(params)
  }
}
