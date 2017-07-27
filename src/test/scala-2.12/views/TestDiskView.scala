package views


import java.util

import com.bwsw.cloudstack.pulse.views.DiskViewBuilder
import org.influxdb.dto._

import scala.collection.JavaConverters._

class TestDiskView {
//  val database = "database"
//
//  var queryResult: QueryResult = _
//  var params: Map[String, String] = _
//
//  val measurement = "disk"
//
//  @Before
//  def initialize() = {
//    params = Map(
//      "uuid" -> "804d6013-e208-3f67-89f0-f60d8e8c8905",
//      "range" -> "1d",
//      "aggregation" -> "1h",
//      "diskUuid" -> "2422847c-05e0-4a65-b0d7-c88d633818a1",
//      "shift" -> "1d"
//    )
//
//    val values: util.List[util.List[AnyRef]] =
//      List (
//        List (
//          "1495610691540557879".asInstanceOf[AnyRef],
//          "-1".asInstanceOf[AnyRef],
//          "1065472".asInstanceOf[AnyRef],
//          "40739193856".asInstanceOf[AnyRef],
//          "26331".asInstanceOf[AnyRef],
//          "1950571".asInstanceOf[AnyRef]
//        ).asJava,
//        List (
//          "1495610691578858087".asInstanceOf[AnyRef],
//          "-1".asInstanceOf[AnyRef],
//          "595729408".asInstanceOf[AnyRef],
//          "28253684736".asInstanceOf[AnyRef],
//          "644056".asInstanceOf[AnyRef],
//          "29431943".asInstanceOf[AnyRef]
//        ).asJava,
//        List (
//          "1495610691578858093".asInstanceOf[AnyRef],
//          "-1".asInstanceOf[AnyRef],
//          "850585726976".asInstanceOf[AnyRef],
//          "178089092096".asInstanceOf[AnyRef],
//          "10298".asInstanceOf[AnyRef],
//          "29431943".asInstanceOf[AnyRef]
//        ).asJava
//      ).asJava
//
//    val series = new QueryResult.Series
//    series.setColumns(List("time", "ioErrors", "readBytes", "writeBytes", "readIOPS", "writeIOPS").asJava)
//    series.setName(measurement)
//    series.setValues(values)
//
//    val result = new QueryResult.Result
//    result.setSeries(List(series).asJava)
//
//    queryResult = new QueryResult
//    queryResult.setResults(List(result).asJava)
//  }
//
//
//  @Test
//  def testPrepareView() = {
//    val viewFabric = new DiskViewFabric
//    val view: DiskViewMeta = viewFabric.prepareMetricsView(queryResult, params)
//
//    Assert.assertEquals(view.measurement, measurement)
//    Assert.assertEquals(view.uuid, params("uuid"))
//    Assert.assertEquals(view.range, params("range"))
//    Assert.assertEquals(view.aggregation, params("aggregation"))
//    Assert.assertEquals(view.shift, params("shift"))
//    Assert.assertEquals(view.diskUuid, params("diskUuid"))
//
//    val headValue = view.result.head
//    val lastValue = view.result.last
//
//    Assert.assertEquals(headValue.ioErrors, "-1")
//    Assert.assertEquals(headValue.readBytes, "1065472")
//    Assert.assertEquals(headValue.writeBytes, "40739193856")
//    Assert.assertEquals(headValue.readIOPS, "26331")
//    Assert.assertEquals(headValue.writeIOPS, "1950571")
//
//    Assert.assertEquals(lastValue.ioErrors, "-1")
//    Assert.assertEquals(lastValue.readBytes, "850585726976")
//    Assert.assertEquals(lastValue.writeBytes, "178089092096")
//    Assert.assertEquals(lastValue.readIOPS, "10298")
//    Assert.assertEquals(lastValue.writeIOPS, "29431943")
//
//    Assert.assertEquals(view.result.length, 3)
//  }
}
