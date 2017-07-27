package com.bwsw.cloudstack.pulse.influx

import org.influxdb.InfluxDB
import org.influxdb.dto._
import org.mockito.Mockito
import org.scalatest.{BeforeAndAfterAll, Matchers, FlatSpec}

import scala.collection.JavaConverters._

class InfluxServiceTests extends FlatSpec with Matchers with BeforeAndAfterAll {

  val username = "username"
  val password = "password"
  val database = "database"
  var queryResult: QueryResult = _

  override def beforeAll(): Unit = {
    val values = List(List("foo".asInstanceOf[AnyRef], "50".asInstanceOf[AnyRef]).asJava).asJava

    val series = new QueryResult.Series
    series.setColumns(List("name", "cpu").asJava)
    series.setName("cpu")
    series.setValues(values)

    val result = new QueryResult.Result
    result.setSeries(List(series).asJava)

    queryResult = new QueryResult
    queryResult.setResults(List(result).asJava)
  }

  it should "Create Influx Connection" in {
    InfluxService.connect("http://localhost:8086", username, password, database)
  }

  it should "Query Influx DB" in {
    val mockInfluxDB = Mockito.mock(classOf[InfluxDB])
    Mockito.when(mockInfluxDB.query(new Query("SELECT * FROM cpu", database))).thenReturn(queryResult)
    InfluxService.influxDB = mockInfluxDB
    InfluxService.dbName = database
    val result = InfluxService.query("SELECT * FROM cpu")
    result.hasError shouldBe false
  }

  override def afterAll(): Unit = {
  }


}


