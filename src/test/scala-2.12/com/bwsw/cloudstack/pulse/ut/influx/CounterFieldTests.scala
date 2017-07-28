package com.bwsw.cloudstack.pulse.ut.influx

import com.bwsw.cloudstack.pulse.influx.CounterField
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class CounterFieldTests extends FlatSpec with Matchers {
  it should "transform units to seconds properly" in {
    CounterField.transformAggregationToSeconds("1m") shouldBe 60
    CounterField.transformAggregationToSeconds("1h") shouldBe 3600
    CounterField.transformAggregationToSeconds("1d") shouldBe 3600 * 24
    CounterField.transformAggregationToSeconds("60h") shouldBe 3600 * 60
    CounterField.transformAggregationToSeconds("60d") shouldBe 3600 * 60 * 24

    intercept[MatchError] {
      List("-1m", "-1h", "-1d", "60.0m", "60.0h", "60.0d", "1", "-1", "1w")
        .foreach(CounterField.transformAggregationToSeconds(_))
    }
  }

  it should "create normal counter expressions based on NON_NEGATIVE_DERIVATIVE and MEAN" in {
    val field1 = CounterField("field", "1h")
    field1.toString() shouldBe """NON_NEGATIVE_DERIVATIVE(MEAN("field"), 1h) / 3600"""

    val field2 = CounterField("field", "1h", " / 30")
    field2.toString() shouldBe """NON_NEGATIVE_DERIVATIVE(MEAN("field"), 1h) / 30 / 3600"""

    val field3 = CounterField("field", "1d")
    field3.toString() shouldBe """NON_NEGATIVE_DERIVATIVE(MEAN("field"), 1d) / 86400"""

    intercept[MatchError] {
      val field = CounterField("field", "-1h")
    }
  }

}
