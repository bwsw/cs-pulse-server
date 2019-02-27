package com.bwsw.cloudstack.pulse.ut.models

import com.bwsw.cloudstack.pulse.models.NetworkInfluxModel
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class NetworkInfluxModelTests extends FlatSpec with Matchers {
    it should "Build correct expression" in {
      val model = new NetworkInfluxModel
      val expr = model.prepareQuery(Map("aggregation" -> "1h", "shift" -> "0h", "range" -> "1d",
        "uuid" -> "550e8400-e29b-41d4-a716-446655440000", "mac" -> "00:11:22:33:44:55"))
      val expectedValue = """SELECT NON_NEGATIVE_DERIVATIVE(MEAN("readErrors"), 1h) / 3600 AS readErrors, """ +
        """NON_NEGATIVE_DERIVATIVE(MEAN("readDrops"), 1h) / 3600 AS readDrops, """ +
        """NON_NEGATIVE_DERIVATIVE(MEAN("writeErrors"), 1h) / 3600 AS writeErrors, """ +
        """NON_NEGATIVE_DERIVATIVE(MEAN("writeDrops"), 1h) / 3600 AS writeDrops, """ +
        """NON_NEGATIVE_DERIVATIVE(MEAN("writePackets"), 1h) / 3600 AS writePackets, """ +
        """NON_NEGATIVE_DERIVATIVE(MEAN("writeBytes"), 1h) * 8 / 3600 AS writeBits, """ +
        """NON_NEGATIVE_DERIVATIVE(MEAN("readPackets"), 1h) / 3600 AS readPackets, """ +
        """NON_NEGATIVE_DERIVATIVE(MEAN("readBytes"), 1h) * 8 / 3600 AS readBits """ +
        """FROM "networkInterface" WHERE "mac" = '00:11:22:33:44:55' AND "vmUuid" = '550e8400-e29b-41d4-a716-446655440000' """ +
        """AND time > now() - 1d - 2h - 0h AND time < now() - 0h GROUP BY time(1h) fill(0) offset 2"""

      expr shouldBe expectedValue
    }
}
