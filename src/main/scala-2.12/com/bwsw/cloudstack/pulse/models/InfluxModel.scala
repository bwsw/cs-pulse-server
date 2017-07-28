package com.bwsw.cloudstack.pulse.models

import com.bwsw.cloudstack.pulse.influx.InfluxService
import org.influxdb.dto.QueryResult
import org.slf4j.LoggerFactory

object InfluxModel {
  protected val logger = LoggerFactory.getLogger(this.getClass)

}

abstract class InfluxModel {
  def getResult(params: Map[String, String]): QueryResult = {
    val query: String = prepareQuery(params)
    InfluxModel.logger.debug(s"Influx Query: `$query'")
    InfluxService.query(query)
  }

  def prepareQuery(params: Map[String, String]): String
}



