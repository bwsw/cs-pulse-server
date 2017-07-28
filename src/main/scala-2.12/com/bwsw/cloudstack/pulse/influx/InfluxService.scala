package com.bwsw.cloudstack.pulse.influx

import org.influxdb._
import org.influxdb.dto._
import org.slf4j.LoggerFactory


/**
  * InfluxUtil object work with Influx database.
  *
  * There are two methods declared here: createConnection, executeQuery.
  * Before use InfluxUtil to executing query call createConnection to create new database connection.
  */

object InfluxService {
  private[pulse] var influxDB: InfluxDB = _
  private[pulse] var dbName: String = _
  private val logger = LoggerFactory.getLogger(this.getClass)

  def connect(serverUrl: String, username: String, password: String, database: String): Unit = {
    influxDB = InfluxDBFactory.connect(serverUrl, username, password)
    dbName = database
    logger.info(s"Connection to InfluxDB: $database @ $serverUrl with $username/$password credentials established.")
  }

  def query(query: String): QueryResult = {
    try {
      if(System.getenv("DEBUG") != null)
        logger.info(query)

      influxDB.query(new Query(query, dbName))
    } catch {
      case re: RuntimeException =>
        re.printStackTrace()
        val error = new QueryResult
        error.setError("Influx Exception Occurred.")
        error
    }
  }
}
