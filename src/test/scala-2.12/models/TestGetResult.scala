package models


import java.util

import com.bwsw.pulse.influx.InfluxUtil
import com.bwsw.pulse.models.Cpu
import org.influxdb.InfluxDB
import org.junit._
import org.influxdb.dto._
import org.mockito.Mockito

import scala.collection.JavaConverters._

class TestGetResult {
  val database = "database"

  var cpuQueryResult: QueryResult = _
  var cpuParams: Map[String, String] = _

  var errorQueryResult: QueryResult = _
  var errorParams: Map[String, String] = _


  @Before
  def initialize() = {
    cpuParams = Map(
      "uuid" -> "1495610691744914660",
      "range" -> "1d",
      "aggregation" -> "1h",
      "shift" -> "1d"
    )

    val values: util.List[util.List[AnyRef]] =
      List (
        List (
          "1495610691540557879".asInstanceOf[AnyRef],
          "13.4375".asInstanceOf[AnyRef]
        ).asJava,
        List (
          "1495610691578858093".asInstanceOf[AnyRef],
          "13.316666666651145".asInstanceOf[AnyRef]
        ).asJava
      ).asJava

    val series = new QueryResult.Series
    series.setColumns(List("time", "cpu").asJava)
    series.setName("cpuTime")
    series.setValues(values)

    val result = new QueryResult.Result
    result.setSeries(List(series).asJava)

    cpuQueryResult = new QueryResult
    cpuQueryResult.setResults(List(result).asJava)


    errorParams = Map(
      "uuid" -> "1a93b7c8-005b-43b6-aef8-6e798b6441b2",
      "range" -> "1dd",
      "aggregation" -> "1h",
      "shift" -> "1d"
      )

    val errorResult = new QueryResult.Result
    result.setSeries(null)

    errorQueryResult = new QueryResult
    errorQueryResult.setResults(List(errorResult).asJava)
    errorQueryResult.setError(null)
  }

  @Test
  def testCorrectGetResult() = {
    val cpu = new Cpu

    val mockInfluxDB = Mockito.mock(classOf[InfluxDB])
    Mockito.when(mockInfluxDB.query(new Query(cpu.prepareQuery(cpuParams), database))).thenReturn(cpuQueryResult)

    InfluxUtil.influxDB = mockInfluxDB
    InfluxUtil.dbName = database

    val result: QueryResult = cpu.getResult(cpuParams)
    Assert.assertEquals(result, cpuQueryResult)
  }

  @Test
  def testIncorrectGetResult() = {
    val cpu = new Cpu

    val mockInfluxDB = Mockito.mock(classOf[InfluxDB])
    Mockito.when(mockInfluxDB.query(new Query(cpu.prepareQuery(errorParams), database))).thenReturn(errorQueryResult)

    InfluxUtil.influxDB = mockInfluxDB
    InfluxUtil.dbName = database

    val result = cpu.getResult(errorParams)
    Assert.assertEquals(result, errorQueryResult)
  }
}
