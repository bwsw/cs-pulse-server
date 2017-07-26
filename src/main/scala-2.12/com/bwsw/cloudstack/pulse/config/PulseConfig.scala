package com.bwsw.cloudstack.pulse.config


import java.io.File

import com.typesafe.config._

import scala.collection.JavaConverters._

case class ScaleConfig(range: String, aggregation: List[String])

case class InfluxConnectionConfig(url: String, username: String, password: String, database: String)

object PulseConfig {
  private val configPath = if (System.getenv("CONFIG") != null) System.getenv("CONFIG") else "/etc/pulse/application.conf"
  private val config = ConfigFactory.parseFile(new File(configPath)).resolve()

  private val aggregationsScope = "pulse_config.scales"
  private val influxScope = "pulse_config.influx"

  private val aggregationKey = "aggregation"
  private val rangeKey = "range"
  private val shiftKey = "pulse_config.shifts"

  private def getInfluxConnectionParameters: InfluxConnectionConfig = {
    val scope = config.getConfig(influxScope)
    InfluxConnectionConfig(scope.getString("url"), scope.getString("username"), scope.getString("password"), scope.getString("database"))
  }

  private def getScales: List[ScaleConfig] = {
    val scope = config.getConfigList(aggregationsScope)
    scope.asScala
      .map(declaration => ScaleConfig(declaration.getString(rangeKey), declaration.getStringList(aggregationKey).asScala.toList)).toList
  }

  /**
    * Available configs
    */
  def scales = getScales

  def ranges = scales.map(cfg => cfg.range)

  def shifts = config.getStringList(shiftKey).asScala.toList

  def influx = getInfluxConnectionParameters

}
