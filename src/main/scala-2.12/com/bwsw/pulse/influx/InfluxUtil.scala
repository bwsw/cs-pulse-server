package com.bwsw.pulse.influx

import org.influxdb._
import org.influxdb.dto._


/**
  * InfluxUtil object work with Influx database.
  *
  * There are two methods declared here: createConnection, executeQuery.
  * Before use InfluxUtil to executing query call createConnection to create new database connection.
  */

object InfluxUtil {
  var influxDB: InfluxDB = _
  var dbName: String = _

  def createConnection(host: String, port: String, username: String, password: String, database: String): Unit = {
    val connUrl = "http://" + host + ":" + port
    this.influxDB = InfluxDBFactory.connect(connUrl, username, password)
    this.dbName = database
  }

  def executeQuery(query: String): QueryResult = {
    influxDB.query(new Query(query, dbName))
  }
}
