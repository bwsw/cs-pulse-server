package controllers

import java.util

import com.bwsw.pulse.config.{PulseConfig, RangeConfig}
import com.bwsw.pulse.controllers.PulseController
import com.bwsw.pulse.influx.InfluxUtil
import org.influxdb.InfluxDB
import org.influxdb.dto.{Query, QueryResult}
import org.scalatra.test.specs2._
import org.mockito.Mockito

import scala.collection.JavaConverters._


class TestCpuController extends MutableScalatraSpec {
  val database = "database"

  val values: util.List[util.List[AnyRef]] =
    List (
      List (
        "foo".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef]
      ).asJava,
      List (
        "bar".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef]
      ).asJava
    ).asJava

  val series = new QueryResult.Series
  series.setColumns(List("name", "cpu").asJava)
  series.setName("cpu")
  series.setValues(values)

  val result = new QueryResult.Result
  result.setSeries(List(series).asJava)

  val queryResult = new QueryResult
  queryResult.setResults(List(result).asJava)


  val mockInfluxDB = Mockito.mock(classOf[InfluxDB])
  val query = new Query("SELECT DERIVATIVE(MEAN(\"cpuTime\"),5m) / LAST(\"cpus\") / 60 * 100 " +
    "AS \"cpu\" FROM \"cpuTime\" WHERE \"vmUuid\" = '550e8400-e29b-41d4-a716-446655440000' " +
    "AND time > now() - 1h - 1d AND time < now() - 1d GROUP BY time(5m)", database)
  Mockito.when(mockInfluxDB.query(query)).thenReturn(queryResult)


  InfluxUtil.influxDB = mockInfluxDB
  InfluxUtil.dbName = database

  addServlet(classOf[PulseController], "/*")

  PulseConfig.range_config = List(
    RangeConfig("15m", List("1m", "5m")),
    RangeConfig("1h", List("5m", "15m")),
    RangeConfig("1d", List("2h", "4h"))
  )
  PulseConfig.range_list = List("15m", "1h", "1d")
  PulseConfig.shift_config = List("m", "h", "d")





  "GET cputime" should {
    "return status 200" in {
      get("/cputime/550e8400-e29b-41d4-a716-446655440000/1h/5m/1d") {
        status must_== 200
      }
    }

    "return status 400" in {
      get("/cputime/12390/1d/1h/1w") {
        status must_== 400
      }
      get("/cputime/550e8400-e29b-41d4-a716-446655440000/1hd/2/hour") {
        status must_== 400
      }
    }

    "return status 404" in {
      get("/cputime") {
        status must_== 404
      }
      get("/cputime/550e8400-e29b-41d4-a716-446655440000") {
        status must_== 404
      }
    }
  }
}
