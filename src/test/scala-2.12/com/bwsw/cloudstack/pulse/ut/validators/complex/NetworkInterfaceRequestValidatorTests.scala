package com.bwsw.cloudstack.pulse.ut.validators.complex

import com.bwsw.cloudstack.pulse.config.PulseConfig
import com.bwsw.cloudstack.pulse.ut.common.ConfigurationTests
import com.bwsw.cloudstack.pulse.validators.complex.{NetworkInterfaceRequestValidator, CPURAMRequestValidator}
import org.scalatest.{BeforeAndAfterAll, Matchers, FlatSpec}

/**
  * Created by Ivan Kudryavtsev on 28.07.17.
  */
class NetworkInterfaceRequestValidatorTests extends FlatSpec with Matchers with BeforeAndAfterAll {

  override def beforeAll() = {
    val configuration = new PulseConfig(ConfigurationTests.resourceConfigPath)
    PulseConfig.configOpt = Some(configuration)
  }

  override def afterAll() = {
    PulseConfig.reset
  }

  it should "validate correct data" in {
    val validator = new NetworkInterfaceRequestValidator
    validator.validate(Map("uuid" -> "550e8400-e29b-41d4-a716-446655440000", "mac" -> "00:11:22:33:44:55", "range" -> "15m", "aggregation" -> "1m", "shift" -> "0h")) shouldBe
      Left("550e8400-e29b-41d4-a716-446655440000/00:11:22:33:44:55/15m/1m/0h")
  }

  it should "validate incorrect data" in {
    val validator = new NetworkInterfaceRequestValidator

    validator.validate(Map("uuid" -> "", "mac" -> "00:11:22:33:44:55", "range" -> "15m", "aggregation" -> "1m", "shift" -> "0h")) shouldBe
      Right("Field 'uuid' haven't passed validation routine. Expected: '^([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})$', Got: ''.")

    validator.validate(Map("uuid" -> "550e8400-e29b-41d4-a716-446655440000", "range" -> "15m", "aggregation" -> "1m", "shift" -> "0h")) shouldBe
      Right("Field 'mac' haven't been found in parameters.")

    validator.validate(Map("uuid" -> "550e8400-e29b-41d4-a716-446655440000", "mac" -> "00+11:22:33:44:55", "range" -> "15m", "aggregation" -> "1m", "shift" -> "0h")) shouldBe
      Right("Field 'mac' haven't passed validation routine. Expected: '^([0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2}[:-][0-9A-Fa-f]{2})$', Got: '00+11:22:33:44:55'.")
  }

}
