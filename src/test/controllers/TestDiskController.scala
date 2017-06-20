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

class TestDiskController extends MutableScalatraSpec {
  val database = "database"

  val values: util.List[util.List[AnyRef]] =
    List (
      List (
        "foo".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef]
      ).asJava,
      List (
        "bar".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef]
      ).asJava
    ).asJava

  val series = new QueryResult.Series
  series.setColumns(List("name", "ioErrors", "readBytes", "writeBytes", "readIOPS", "writeIOPS").asJava)
  series.setName("disk")
  series.setValues(values)

  val result = new QueryResult.Result
  result.setSeries(List(series).asJava)

  val queryResult = new QueryResult
  queryResult.setResults(List(result).asJava)


  val mockInfluxDB = Mockito.mock(classOf[InfluxDB])
  val query = new Query("SELECT MEAN(\"ioErrors\") AS \"ioErrors\", MEAN(\"readBytes\") " +
    "AS \"readBytes\", MEAN(\"writeBytes\") AS \"writeBytes\", MEAN(\"readIOPS\") " +
    "AS \"readIOPS\", MEAN(\"writeIOPS\") AS \"writeIOPS\" FROM \"disk\" " +
    "WHERE \"vmUuid\" = '550e8400-e29b-41d4-a716-446655440000' " +
    "AND \"image\" = '70dc25e9-82c6-4a8c-8d7d-3e304cced576' " +
    "AND time > now() - 1h - 0d AND time < now() - 0d GROUP BY time(15m)", database)
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




  "GET disk" should {
    "return status 200" in {
      get("/disk/550e8400-e29b-41d4-a716-446655440000/70dc25e9-82c6-4a8c-8d7d-3e304cced576/1h/15m/0d") {
        status must_== 200
      }
    }

    "return status 400" in {
      get("/disk/550e8400-e29b-41d4-a716-446655440000/70dc25e9/1h/15m/0s") {
        status must_== 400
      }
      get("/disk/446655440000/70dc25e9-82c6-4a8c-8d7d-3e304cced576/1h/15m/0s") {
        status must_== 400
      }
      get("/disk/550e8400-e29b-41d4-a716-446655440000/70dc25e9-82c6-4a8c-8d7d-3e304cced576/1h/15md/0s") {
        status must_== 400
      }
    }

    "return status 404" in {
      get("/disk") {
        status must_== 404
      }
      get("/disk/550e8400-e29b-41d4-a716-446655440000/1h/15m/0sd") {
        status must_== 404
      }
    }
  }
}
