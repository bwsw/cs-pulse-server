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

object InfluxUtil {
  private var influxDB: InfluxDB = _
  private var dbName: String = _
  private val logger = LoggerFactory.getLogger(this.getClass)

  def createConnection(serverUrl: String, username: String, password: String, database: String): Unit = {
    this.influxDB = InfluxDBFactory.connect(serverUrl, username, password)
    this.dbName = database
  }

  def executeQuery(query: String): QueryResult = {
    try {
      influxDB.query(new Query(query, dbName))
    } catch {
      case re: RuntimeException =>
        val error = new QueryResult
        error.setError("Influx Exception Occured.")
        error
    }
  }
}
