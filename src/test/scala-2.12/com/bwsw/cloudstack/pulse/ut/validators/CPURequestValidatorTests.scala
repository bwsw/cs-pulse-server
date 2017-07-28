package com.bwsw.cloudstack.pulse.ut.validators

import com.bwsw.cloudstack.pulse.config.PulseConfig
import com.bwsw.cloudstack.pulse.ut.common.ConfigurationTests
import com.bwsw.cloudstack.pulse.validators.CPURequestValidator
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

/**
  * Created by Ivan Kudryavtsev on 28.07.17.
  */
class CPURequestValidatorTests extends FlatSpec with Matchers with BeforeAndAfterAll {
  override def beforeAll() = {
    val configuration = new PulseConfig(ConfigurationTests.resourceConfigPath)
    PulseConfig.configOpt = Some(configuration)
  }

  it should "validate correct data" in {
    val validator = new CPURequestValidator
    validator.validate(Map("uuid" -> "550e8400-e29b-41d4-a716-446655440000", "range" -> "15m", "aggregation" -> "1m", "shift" -> "0h")) shouldBe
      Left("550e8400-e29b-41d4-a716-446655440000/15m/1m/0h")

    validator.validate(Map("uuid" -> "", "range" -> "15m", "aggregation" -> "1m", "shift" -> "0h")) shouldBe
      Right("Field 'uuid' haven't passed validation routine. Expected: '^([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})$', Got: ''.")

    validator.validate(Map("range" -> "15m", "aggregation" -> "1m", "shift" -> "0h")) shouldBe
      Right("Field 'uuid' haven't been found in parameters.")
  }

  override def afterAll() = {
    PulseConfig.reset
  }
}
