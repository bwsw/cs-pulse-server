package com.bwsw.cloudstack.pulse.ut.validators

import com.bwsw.cloudstack.pulse.config.PulseConfig
import com.bwsw.cloudstack.pulse.ut.common.ConfigurationTests
import com.bwsw.cloudstack.pulse.validators.TimeScaleValidator
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 28.07.17.
  */
class TimeScaleValidatorTests extends FlatSpec with Matchers with BeforeAndAfterAll {

  override def beforeAll() = {
    val configuration = new PulseConfig(ConfigurationTests.resourceConfigPath)
    PulseConfig.configOpt = Some(configuration)
  }

  it should "validate correct data" in {
    val validator = new TimeScaleValidator()
    validator.validate(Map("range" -> "15m", "aggregation" -> "1m", "shift" -> "0h")) shouldBe Left("15m/1m/0h")
    validator.validate(Map("range" -> "15m", "aggregation" -> "5m", "shift" -> "0h")) shouldBe Left("15m/5m/0h")
    validator.validate(Map("range" -> "30m", "aggregation" -> "5m", "shift" -> "0d")) shouldBe Left("30m/5m/0d")

    validator.validate(Map("range" -> "60m", "aggregation" -> "5m", "shift" -> "0d")) shouldBe
      Right("TimeScale parameters [Range '60m', Aggregation '5m', Shift: '0d'] haven't passed the validation.")

    validator.validate(Map("range" -> "30m", "aggregation" -> "5h", "shift" -> "0d")) shouldBe
      Right("TimeScale parameters [Range '30m', Aggregation '5h', Shift: '0d'] haven't passed the validation.")

    validator.validate(Map("range" -> "30m", "aggregation" -> "5m", "shift" -> "-1d")) shouldBe
      Right("Field 'shift' haven't passed validation routine. Expected: '^([0-9]+[mhd])$', Got: '-1d'.")

  }

  override def afterAll() = {
    PulseConfig.reset
  }
}
