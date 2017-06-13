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

class TestNetworkController extends MutableScalatraSpec{
  val database = "database"

  val values: util.List[util.List[AnyRef]] =
    List (
      List (
        "foo".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef],
        "50".asInstanceOf[AnyRef],
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
        "60".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef],
        "60".asInstanceOf[AnyRef]
      ).asJava
    ).asJava

  val series = new QueryResult.Series
  series.setColumns(List("name",
    "readBytes", "writeBytes",
    "readErrors", "writeErrors",
    "readDrops", "writeDrops",
    "readPackets", "writePackets").asJava)
  series.setName("network")
  series.setValues(values)

  val result = new QueryResult.Result
  result.setSeries(List(series).asJava)

  val queryResult = new QueryResult
  queryResult.setResults(List(result).asJava)


  val mockInfluxDB = Mockito.mock(classOf[InfluxDB])
  val query = new Query("SELECT MEAN(\"ioErrors\") AS \"ioErrors\", MEAN(\"readBytes\") " +
    "AS \"readBytes\", MEAN(\"writeBytes\") AS \"writeBytes\", MEAN(\"readIOPS\") " +
    "AS \"readIOPS\", MEAN(\"writeIOPS\") AS \"writeIOPS\" FROM \"networkInterface\" " +
    "WHERE \"vmUuid\" = '550e8400-e29b-41d4-a716-446655440000' " +
    "AND \"mac\" = '08:ED:B9:49:B2:E5' AND time > now() - 1h - 0d " +
    "AND time < now() - 0d GROUP BY time(15m)", database)
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





  "GET network" should {
    "return status 200" in {
      get("/network-interface/550e8400-e29b-41d4-a716-446655440000/08:ED:B9:49:B2:E5/1h/15m/0d") {
        status must_==200
      }
    }

    "return status 400" in {
      get("/network-interface/550e8400-e29b-41d4-a716-446655440000/08:ED:B9/1h/15m/0s") {
        status must_== 400
      }
      get("/network-interface/550e8400/08:ED:B9:49:B2:E5/1h/15m/0s") {
        status must_== 400
      }
      get("/network-interface/550e8400-e29b-41d4-a716-446655440000/08:ED:B9:49:B2:E5/1h/15m2/0s") {
        status must_== 400
      }
    }

    "return status 404" in {
      get("/network-interface") {
        status must_== 404
      }
      get("/network-interface/550e8400-e29b-41d4-a716-446655440000/1h/15m/0s") {
        status must_== 404
      }
    }
  }
}
