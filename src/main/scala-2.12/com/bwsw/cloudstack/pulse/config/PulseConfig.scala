package com.bwsw.cloudstack.pulse.config


import java.io.File
import java.nio.file.{Paths, Files}

import com.typesafe.config._

import scala.collection.JavaConverters._

case class ScaleConfig(range: String, aggregations: List[String])

case class InfluxConnectionConfig(url: String, username: String, password: String, database: String)

object PulseConfig {

  private[pulse] var configOpt: Option[PulseConfig] = None
  def apply() = {
    if (configOpt.isEmpty) {
      val configPath = {
        val envPath = System.getenv("CONFIG")
        if (envPath != null) envPath else "/etc/pulse/application.conf"
      }
      configOpt = Some(new PulseConfig(configPath))
    }
    configOpt.get
  }

  private[pulse] def reset: Unit = {
    configOpt = None
  }
}

class PulseConfig(configPath: String) {

  require(Files.exists(Paths.get(configPath)))

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

  private def getScales: Map[String, ScaleConfig] = {
    val scope = config.getConfigList(aggregationsScope)
    scope.asScala
      .map(scale => scale.getString(rangeKey) -> ScaleConfig(scale.getString(rangeKey), scale.getStringList(aggregationKey).asScala.toList)).toMap
  }

  private def getScalesList: List[(String, ScaleConfig)] = {
    val scope = config.getConfigList(aggregationsScope)
    scope.asScala
      .map(scale => (scale.getString(rangeKey),
        ScaleConfig(scale.getString(rangeKey), scale.getStringList(aggregationKey).asScala.toList))).toList
  }

  /**
    * Available configs
    */
  def scales = getScales

  def scalesList = getScalesList

  def ranges = scalesList.map(cfg => cfg._1)

  def shifts = config.getStringList(shiftKey).asScala.toList

  def influx = getInfluxConnectionParameters
}
