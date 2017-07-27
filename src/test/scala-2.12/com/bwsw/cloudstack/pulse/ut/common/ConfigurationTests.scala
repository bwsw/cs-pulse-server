package com.bwsw.cloudstack.pulse.ut.common

import com.bwsw.cloudstack.pulse.config.{InfluxConnectionConfig, PulseConfig, ScaleConfig}
import org.scalatest.{FlatSpec, Matchers}

object ConfigurationTests {
  val resourceConfigPath = "src/main/resources/application-test-conf.conf"
}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class ConfigurationTests extends FlatSpec with Matchers  {

  it should "Open config from resources properly" in {
    PulseConfig.reset
    val configuration = new PulseConfig(ConfigurationTests.resourceConfigPath)
    configuration.shifts shouldBe List("m", "h", "d")
    configuration.influx shouldBe InfluxConnectionConfig("http://localhost:8086/","username","password","database")
    configuration.ranges shouldBe List("15m", "30m")
    configuration.scales shouldBe Map("15m" -> ScaleConfig("15m", List("1m", "5m")), "30m" -> ScaleConfig("30m", List("1m", "5m", "15m")))
  }
}
