package com.bwsw.cloudstack.pulse.ut.influx

import com.bwsw.cloudstack.pulse.influx.{GaugeField, QueryBuilder}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class QueryBuilderTests extends FlatSpec with Matchers {
  it should "Generate correct expressions" in {
    val expr = QueryBuilder()
      .select
        .field("name",GaugeField("name"))
      .from("table")
      .where
        .andEq("a", "b")
        .timeSpan("1h", "1m", "0m")
      .groupByAggregation
      .build
    expr shouldBe """SELECT MEAN("name") AS name FROM "table" WHERE "a" = 'b' AND time > now() - 1m - 0m AND time < now() - 0m GROUP BY time(1h)"""
  }
}
