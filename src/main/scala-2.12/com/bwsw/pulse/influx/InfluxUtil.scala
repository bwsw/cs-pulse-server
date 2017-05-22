package com.bwsw.pulse.influx

import com.paulgoldbaum.influxdbclient.Parameter.Precision
import com.paulgoldbaum.influxdbclient.{Database, InfluxDB, QueryResult}

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global



object InfluxUtil {
  val influxConnection: InfluxDB = InfluxDB
    .connect(host = "cs1-influx.z1.netpoint-dc.com", port = 8086, username = "puls", password = "pulsPassword")

  val influxDB: Database = influxConnection.selectDatabase("puls")

  def getResult() = {
    val query = "select \"cpuTime\" from \"cpuTime\" where time > now() - 1m"
    val data: QueryResult = Await.result(influxDB.query(query, Precision.MICROSECONDS), 5000.millis)
    val result = data.series.head
    println(result)
    influxConnection.close()

  }

  def executeQuery(query: String): QueryResult = {
    Await.result(influxDB.query(query, Precision.MICROSECONDS), 5000.millis)
  }
}



