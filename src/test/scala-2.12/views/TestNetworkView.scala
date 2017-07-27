package views

import java.util

import com.bwsw.cloudstack.pulse.views.NetworkViewBuilder
import org.influxdb.dto._

import scala.collection.JavaConverters._


class TestNetworkView {
//  val database = "database"
//
//  var queryResult: QueryResult = _
//  var params: Map[String, String] = _
//
//  val measurement = "network"
//
//  @Before
//  def initialize() = {
//    params = Map(
//      "uuid" -> "804d6013-e208-3f67-89f0-f60d8e8c8905",
//      "range" -> "1d",
//      "aggregation" -> "1h",
//      "mac" -> "06:76:8c:00:01:bd",
//      "shift" -> "1d"
//    )
//
//    val values: util.List[util.List[AnyRef]] =
//      List (
//        List (
//          "1495610691540557879".asInstanceOf[AnyRef],
//          "5088419624".asInstanceOf[AnyRef],
//          "4941715983".asInstanceOf[AnyRef],
//          "0".asInstanceOf[AnyRef],
//          "0".asInstanceOf[AnyRef],
//          "11218".asInstanceOf[AnyRef],
//          "0".asInstanceOf[AnyRef],
//          "38733110".asInstanceOf[AnyRef],
//          "166636438".asInstanceOf[AnyRef]
//        ).asJava,
//        List (
//          "1495610691578858087".asInstanceOf[AnyRef],
//          "55355335836".asInstanceOf[AnyRef],
//          "6528511958".asInstanceOf[AnyRef],
//          "0".asInstanceOf[AnyRef],
//          "0".asInstanceOf[AnyRef],
//          "11220".asInstanceOf[AnyRef],
//          "0".asInstanceOf[AnyRef],
//          "38733110".asInstanceOf[AnyRef],
//          "166636438".asInstanceOf[AnyRef]
//        ).asJava,
//        List (
//          "1495610691578858093".asInstanceOf[AnyRef],
//          "1353640310".asInstanceOf[AnyRef],
//          "1536696880".asInstanceOf[AnyRef],
//          "0".asInstanceOf[AnyRef],
//          "0".asInstanceOf[AnyRef],
//          "22222".asInstanceOf[AnyRef],
//          "14".asInstanceOf[AnyRef],
//          "75331218".asInstanceOf[AnyRef],
//          "2095912".asInstanceOf[AnyRef]
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
//    val viewFabric = new NetworkViewFabric
//    val view: NetworkViewMeta = viewFabric.prepareMetricsView(queryResult, params)
//
//    Assert.assertEquals(view.measurement, measurement)
//    Assert.assertEquals(view.uuid, params("uuid"))
//    Assert.assertEquals(view.range, params("range"))
//    Assert.assertEquals(view.aggregation, params("aggregation"))
//    Assert.assertEquals(view.shift, params("shift"))
//    Assert.assertEquals(view.mac, params("mac"))
//
//    val headValue = view.result.head
//    val lastValue = view.result.last
//
//    Assert.assertEquals(headValue.readBytes, "5088419624")
//    Assert.assertEquals(headValue.writeBytes, "4941715983")
//    Assert.assertEquals(headValue.readErrors, "0")
//    Assert.assertEquals(headValue.writeErrors, "0")
//    Assert.assertEquals(headValue.readDrops, "11218")
//    Assert.assertEquals(headValue.writeDrops, "0")
//    Assert.assertEquals(headValue.readPackets, "38733110")
//    Assert.assertEquals(headValue.writePackets, "166636438")
//
//    Assert.assertEquals(lastValue.readBytes, "1353640310")
//    Assert.assertEquals(lastValue.writeBytes, "1536696880")
//    Assert.assertEquals(lastValue.readErrors, "0")
//    Assert.assertEquals(lastValue.writeErrors, "0")
//    Assert.assertEquals(lastValue.readDrops, "22222")
//    Assert.assertEquals(lastValue.writeDrops, "14")
//    Assert.assertEquals(lastValue.readPackets, "75331218")
//    Assert.assertEquals(lastValue.writePackets, "2095912")
//
//    Assert.assertEquals(view.result.length, 3)
//  }
}
