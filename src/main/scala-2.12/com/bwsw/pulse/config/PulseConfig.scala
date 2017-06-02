package com.bwsw.pulse.config


import com.typesafe.config._
import scala.collection.JavaConverters._

case class RangeConfig(range: String, allowed_aggregation: List[String])
case class InfluxConnection(host: String, port: String, username: String, password: String, database: String)


object PulseConfig {
  private val conf = ConfigFactory.load()

  private val aggregations_allowed = "pulse_config.aggregations_allowed"
  private val influx_config_name = "pulse_config.influx"
  private val aggregation_config_name = "aggregation"
  private val range_config_name = "range"
  private val shift_config_name = "pulse_config.shift"

  private def renderInfluxConfig: InfluxConnection = {
    val influx_config = conf.getConfig(influx_config_name)
    InfluxConnection(
      influx_config.getString("host"),
      influx_config.getString("port"),
      influx_config.getString("username"),
      influx_config.getString("password"),
      influx_config.getString("database")
    )
  }

  private def renderRangeConfig: List[RangeConfig] = {
    val aggr_allowed = conf.getConfigList(aggregations_allowed)
    aggr_allowed.asScala.map {
      range_config => RangeConfig(range_config.getString(range_config_name),
        range_config.getStringList(aggregation_config_name).asScala.toList)
    }.toList
  }

  /**
    * Available configs
    */
  var range_config: List[RangeConfig] = renderRangeConfig
  var range_list: List[String] = range_config.map(cfg => cfg.range)
  var shift_config: List[String] = conf.getStringList(shift_config_name).asScala.toList
  var influx_connection: InfluxConnection = renderInfluxConfig

}
