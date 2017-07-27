package com.bwsw.cloudstack.pulse.ut.common

import com.bwsw.cloudstack.pulse.config.{InfluxConnectionConfig, PulseConfig}
import org.scalatest.{Matchers, FlatSpec}

object ConfigurationTests {
  val resourceConfigPath = "src/main/resources/application.conf"
}

/**
  * Created by Ivan Kudryavtsev on 27.07.17.
  */
class ConfigurationTests extends FlatSpec with Matchers  {

  it should "Open config from resources properly" in {
    val configuration = new PulseConfig(ConfigurationTests.resourceConfigPath)
    configuration.shifts shouldBe List("m", "h", "d")
    configuration.influx shouldBe InfluxConnectionConfig("http://localhost:8086/","username","password","database")
  }
}
