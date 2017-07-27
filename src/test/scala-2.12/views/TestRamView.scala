package views

import java.util

import com.bwsw.cloudstack.pulse.views.{RamViewFabric, RamViewMeta}
import org.influxdb.dto.QueryResult

import scala.collection.JavaConverters._


class TestRamView {
//  val database = "database"
//
//  var queryResult: QueryResult = _
//  var params: Map[String, String] = _
//
//  val measurement = "ram"
//
//  @Before
//  def initialize() = {
//    params = Map(
//      "uuid" -> "804d6013-e208-3f67-89f0-f60d8e8c8905",
//      "range" -> "1h",
//      "aggregation" -> "15m",
//      "shift" -> "1d"
//    )
//
//    val values: util.List[util.List[AnyRef]] =
//      List (
//        List (
//          "1495610691540557879".asInstanceOf[AnyRef],
//          "4242096".asInstanceOf[AnyRef]
//        ).asJava,
//        List (
//          "1495610691578858087".asInstanceOf[AnyRef],
//          "1225272".asInstanceOf[AnyRef]
//        ).asJava,
//        List (
//          "1495610691578858093".asInstanceOf[AnyRef],
//          "4290544".asInstanceOf[AnyRef]
//        ).asJava
//      ).asJava
//
//    val series = new QueryResult.Series
//    series.setColumns(List("time", "rss").asJava)
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
//    val viewFabric = new RamViewFabric
//    val view: RamViewMeta = viewFabric.prepareMetricsView(queryResult, params)
//
//    Assert.assertEquals(view.measurement, measurement)
//    Assert.assertEquals(view.uuid, params("uuid"))
//    Assert.assertEquals(view.range, params("range"))
//    Assert.assertEquals(view.aggregation, params("aggregation"))
//    Assert.assertEquals(view.shift, params("shift"))
//
//    Assert.assertEquals(view.result.head.rss, "4242096")
//    Assert.assertEquals(view.result.last.rss, "4290544")
//
//    Assert.assertEquals(view.result.length, 3)
//  }
}
