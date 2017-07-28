package com.bwsw.cloudstack.pulse.ut.models

import com.bwsw.cloudstack.pulse.models.DiskInfluxModel
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class DiskInfluxModelTests extends FlatSpec with Matchers {
  it should "Build correct expression" in {
    val model = new DiskInfluxModel
    val expr = model.prepareQuery(Map("aggregation" -> "1h", "shift" -> "0h", "range" -> "1d",
      "uuid" -> "550e8400-e29b-41d4-a716-446655440000", "diskUuid" -> "550e8400-e29b-41d4-a716-446655441111"))

    val expectedValue = """SELECT NON_NEGATIVE_DERIVATIVE(MEAN("readIOPS"), 1h) / 3600 AS readIOPS, """ +
      """NON_NEGATIVE_DERIVATIVE(MEAN("ioErrors"), 1h) / 3600 AS ioErrors, """ +
      """NON_NEGATIVE_DERIVATIVE(MEAN("readBytes"), 1h) / 3600 AS readBytes, """ +
      """NON_NEGATIVE_DERIVATIVE(MEAN("writeBytes"), 1h) / 3600 AS writeBytes, """ +
      """NON_NEGATIVE_DERIVATIVE(MEAN("writeIOPS"), 1h) / 3600 AS writeIOPS """ +
      """FROM "disk" WHERE "vmUuid" = '550e8400-e29b-41d4-a716-446655440000' AND """ +
      """"image" = '550e8400-e29b-41d4-a716-446655441111' AND time > now() - 1d - 0h AND time < now() - 0h GROUP BY time(1h) fill(0)"""

    expr shouldBe expectedValue
  }
}
