package com.bwsw.cloudstack.pulse.ut.influx

import com.bwsw.cloudstack.pulse.influx.GaugeField
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class GaugeFieldTests extends FlatSpec with Matchers {
  it should "create normal gauge expressions based on MEAN" in {
    val field1 = GaugeField("field")
    field1.toString() shouldBe """MEAN("field")"""

    val bitField = GaugeField("field", " * 8")
    bitField.toString() shouldBe """MEAN("field") * 8"""
  }
}
