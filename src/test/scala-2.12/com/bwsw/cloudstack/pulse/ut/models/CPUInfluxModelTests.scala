package com.bwsw.cloudstack.pulse.ut.models

import com.bwsw.cloudstack.pulse.models.CPUInfluxModel
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class CPUInfluxModelTests extends FlatSpec with Matchers {
  it should "Build correct expression" in {
    val model = new CPUInfluxModel
    val expr = model.prepareQuery(Map("aggregation" -> "1h", "shift" -> "0h", "range" -> "1d", "uuid" -> "550e8400-e29b-41d4-a716-446655440000"))
    expr shouldBe """SELECT MEAN("cpus") AS cpuCount, NON_NEGATIVE_DERIVATIVE(MEAN("cpuTime"), 1h) / LAST("cpus") * 100 / 3600 AS cpuTime FROM """ +
      """"cpuTime" WHERE "vmUuid" = '550e8400-e29b-41d4-a716-446655440000' AND time > now() - 1d - 0h AND time < now() - 0h GROUP BY time(1h)"""
  }
}
