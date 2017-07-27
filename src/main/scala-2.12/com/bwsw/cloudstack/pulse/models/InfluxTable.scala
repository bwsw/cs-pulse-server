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
    InfluxTable.logger.info(s"Influx Query: `$query'")
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
        .field("cpuTime", CounterField("cpuTime", aggregation, """ / LAST("cpus") * 100""").toString())
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
        .field("rss", GaugeField("rss").toString())
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
        .field("ioErrors",   CounterField("ioErrors", aggregation).toString())
        .field("readBytes",  CounterField("readBytes", aggregation).toString())
        .field("writeBytes", CounterField("writeBytes", aggregation).toString())
        .field("readIOPS",   CounterField("readIOPS", aggregation).toString())
        .field("writeIOPS",  CounterField("writeIOPS", aggregation).toString())
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
        .field("readBits",      CounterField("readBytes", aggregation, " * 8").toString())
        .field("writeBits",     CounterField("writeBytes", aggregation, " * 8").toString())
        .field("readErrors",    CounterField("readErrors", aggregation).toString())
        .field("writeErrors",   CounterField("writeErrors", aggregation).toString())
        .field("readDrops",     CounterField("readDrops", aggregation).toString())
        .field("writeDrops",    CounterField("writeDrops", aggregation).toString())
        .field("readPackets",   CounterField("readPackets", aggregation).toString())
        .field("writePackets",  CounterField("writePackets", aggregation).toString())
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
