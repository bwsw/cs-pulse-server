package com.bwsw.cloudstack.pulse.ut.models

import com.bwsw.cloudstack.pulse.models.RAMInfluxModel
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class RAMInfluxModelTests extends FlatSpec with Matchers {
  it should "Build correct expression" in {
    val model = new RAMInfluxModel
    val expr = model.prepareQuery(Map("aggregation" -> "1h", "shift" -> "0h", "range" -> "1d", "uuid" -> "550e8400-e29b-41d4-a716-446655440000"))
    expr shouldBe """SELECT MEAN("rss") AS ram FROM "rss" """ +
      """WHERE "vmUuid" = '550e8400-e29b-41d4-a716-446655440000' AND time > now() - 1d - 2h - 0h AND time < now() - 0h GROUP BY time(1h) fill(0) offset 2"""
  }
}
