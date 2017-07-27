package com.bwsw.cloudstack.pulse.ut.influx

import com.bwsw.cloudstack.pulse.influx.CounterField
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class CounterFieldTests extends FlatSpec with Matchers {
  it should "transfer units to seconds properly" in {
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
}
