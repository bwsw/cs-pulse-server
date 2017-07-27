package com.bwsw.cloudstack.pulse.models

import com.bwsw.cloudstack.pulse.influx.{CounterField, QueryBuilder}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class DiskInfluxTable extends InfluxModel {
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
