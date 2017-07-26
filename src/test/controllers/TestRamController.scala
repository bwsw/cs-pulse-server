package controllers


import java.util

import com.bwsw.cloudstack.pulse.config.{PulseConfig, ScaleConfig}
import com.bwsw.cloudstack.pulse.controllers.PulseController
import com.bwsw.cloudstack.pulse.influx.InfluxUtil
import org.influxdb.InfluxDB
import org.influxdb.dto.{Query, QueryResult}
import org.scalatra.test.specs2._
import org.mockito.Mockito

import scala.collection.JavaConverters._


class TestRamController extends MutableScalatraSpec {

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
  series.setColumns(List("name", "rss").asJava)
  series.setName("ram")
  series.setValues(values)

  val result = new QueryResult.Result
  result.setSeries(List(series).asJava)

  val queryResult = new QueryResult
  queryResult.setResults(List(result).asJava)


  val mockInfluxDB = Mockito.mock(classOf[InfluxDB])
  val query = new Query("SELECT MEAN(\"rss\") AS \"rss\" FROM \"rss\" " +
    "WHERE \"vmUuid\" = '550e8400-e29b-41d4-a716-446655440000' " +
    "AND time > now() - 1d - 1d AND time < now() - 1d GROUP BY time(2h)", database)
  Mockito.when(mockInfluxDB.query(query)).thenReturn(queryResult)


  InfluxUtil.influxDB = mockInfluxDB
  InfluxUtil.dbName = database

  addServlet(classOf[PulseController], "/*")

  PulseConfig.scales = List(
    ScaleConfig("15m", List("1m", "5m")),
    ScaleConfig("1h", List("5m", "15m")),
    ScaleConfig("1d", List("2h", "4h"))
  )
  PulseConfig.ranges = List("15m", "1h", "1d")
  PulseConfig.shifts = List("m", "h", "d")





  "GET ram" should {
    "return status 200" in {
      get("/ram/550e8400-e29b-41d4-a716-446655440000/1d/2h/1d") {
        status must_== 200
      }
    }

    "return status 400" in {
      get("/ram/446655440000/1d/1h/1w") {
        status must_== 400
      }
      get("/ram/550e8400-e29b-41d4-a716-446655440000/1d/1hd/1w") {
        status must_== 400
      }
    }

    "return status 404" in {
      get("/ram") {
        status must_== 404
      }
      get("/ram/550e8400-e29b-41d4-a716-446655440000") {
        status must_== 404
      }
    }
  }
}
