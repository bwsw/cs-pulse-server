package com.bwsw.cloudstack.pulse.models

import com.bwsw.cloudstack.pulse.influx.{CounterField, GaugeField, QueryBuilder}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class CPUInfluxModel extends InfluxModel {
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
