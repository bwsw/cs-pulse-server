package com.bwsw.cloudstack.pulse.models

import com.bwsw.cloudstack.pulse.influx.{CounterField, QueryBuilder}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */

class NetworkInfluxModel extends InfluxModel {
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
