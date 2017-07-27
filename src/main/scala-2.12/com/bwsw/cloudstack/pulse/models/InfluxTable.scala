package com.bwsw.cloudstack.pulse.models

import com.bwsw.cloudstack.pulse.influx.{CounterField, GaugeField, InfluxService, QueryBuilder}
import org.influxdb.dto.QueryResult
import org.slf4j.LoggerFactory

object InfluxTable {
  protected val logger = LoggerFactory.getLogger(this.getClass)

}

abstract class InfluxTable {
  def getResult(params: Map[String, String]): QueryResult = {
    val query: String = prepareQuery(params)
    InfluxTable.logger.debug(s"Influx Query: `$query'")
    InfluxService.query(query)
  }

  def prepareQuery(params: Map[String, String]): String
}

class CpuInfluxTable extends InfluxTable {
  override def prepareQuery(params: Map[String, String]): String = {
    val aggregation = params("aggregation")
    val shift = params("shift")
    val range = params("range")
    val q = QueryBuilder()
      .select
        .field("cpuCount", GaugeField("cpus"))
        .field("cpuTime", CounterField("cpuTime", aggregation, """ / LAST("cpus") * 100"""))
      .from("cpuTime")
      .where
        .andEq("vmUuid", params("uuid"))
        .timeSpan(aggregation, range, shift)
      .groupByAggregation
      .build
    q
  }
}

class RAMInfluxTable extends InfluxTable {
  override def prepareQuery(params: Map[String, String]): String = {
    val aggregation = params("aggregation")
    val shift = params("shift")
    val range = params("range")
    val q = QueryBuilder()
      .select
        .field("ram", GaugeField("rss"))
      .from("rss")
      .where
        .andEq("vmUuid", params("uuid"))
        .timeSpan(aggregation, range, shift)
      .groupByAggregation
      .build
    q
  }
}

class DiskInfluxTable extends InfluxTable {
  override def prepareQuery(params: Map[String, String]): String = {
    val aggregation = params("aggregation")
    val shift = params("shift")
    val range = params("range")
    val q = QueryBuilder()
      .select
        .field("ioErrors",   CounterField("ioErrors", aggregation))
        .field("readBytes",  CounterField("readBytes", aggregation))
        .field("writeBytes", CounterField("writeBytes", aggregation))
        .field("readIOPS",   CounterField("readIOPS", aggregation))
        .field("writeIOPS",  CounterField("writeIOPS", aggregation))
      .from("disk")
      .where
        .andEq("vmUuid", params("uuid"))
        .andEq("image", params("diskUuid"))
        .timeSpan(aggregation, range, shift)
      .groupByAggregation
      .build
    q
  }
}

class NetworkInfluxTable extends InfluxTable {
  override def prepareQuery(params: Map[String, String]): String = {
    val aggregation = params("aggregation")
    val shift = params("shift")
    val range = params("range")
    val q = QueryBuilder()
      .select
        .field("readBits",      CounterField("readBytes", aggregation, " * 8"))
        .field("writeBits",     CounterField("writeBytes", aggregation, " * 8"))
        .field("readErrors",    CounterField("readErrors", aggregation))
        .field("writeErrors",   CounterField("writeErrors", aggregation))
        .field("readDrops",     CounterField("readDrops", aggregation))
        .field("writeDrops",    CounterField("writeDrops", aggregation))
        .field("readPackets",   CounterField("readPackets", aggregation))
        .field("writePackets",  CounterField("writePackets", aggregation))
      .from("networkInterface")
      .where
        .andEq("vmUuid", params("uuid"))
        .andEq("mac", params("mac"))
        .timeSpan(aggregation, range, shift)
      .groupByAggregation
      .build
    q
  }
}
