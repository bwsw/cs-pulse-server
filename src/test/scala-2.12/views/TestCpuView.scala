package views

import java.util

import com.bwsw.cloudstack.pulse.views.{CpuViewFabric, CpuViewMeta}
import org.junit._
import org.influxdb.dto._

import scala.collection.JavaConverters._

class TestCpuView {

  val database = "database"

  var queryResult: QueryResult = _
  var params: Map[String, String] = _

  var brokenQueryResult: QueryResult = _

  val measurement = "cputime"

  @Before
  def initialize() = {
    params = Map(
      "uuid" -> "804d6013-e208-3f67-89f0-f60d8e8c8905",
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
          "1495610691578858087".asInstanceOf[AnyRef],
          "13.316666".asInstanceOf[AnyRef]
        ).asJava,
        List (
          "1495610691578858093".asInstanceOf[AnyRef],
          "13.316666666651145".asInstanceOf[AnyRef]
        ).asJava
      ).asJava

    val series = new QueryResult.Series
    series.setColumns(List("time", "cpu").asJava)
    series.setName(measurement)
    series.setValues(values)

    val result = new QueryResult.Result
    result.setSeries(List(series).asJava)

    queryResult = new QueryResult
    queryResult.setResults(List(result).asJava)


    val brokenValues: util.List[util.List[AnyRef]] =
      List (
        List (
          "1495610691540557879".asInstanceOf[AnyRef],
          null
        ).asJava,
        List (
          "1495610691578858087".asInstanceOf[AnyRef],
          "13.316666".asInstanceOf[AnyRef]
        ).asJava,
        List (
          "1495610691578858093".asInstanceOf[AnyRef],
          "13.316666666651145".asInstanceOf[AnyRef]
        ).asJava
      ).asJava

    val brokenSeries = new QueryResult.Series
    brokenSeries.setColumns(List("time", "cpu").asJava)
    brokenSeries.setName(measurement)
    brokenSeries.setValues(brokenValues)

    val brokenResult = new QueryResult.Result
    brokenResult.setSeries(List(brokenSeries).asJava)

    brokenQueryResult = new QueryResult
    brokenQueryResult.setResults(List(brokenResult).asJava)

  }


  @Test
  def testPrepareView() = {
    val viewFabric = new CpuViewFabric
    val view: CpuViewMeta = viewFabric.prepareMetricsView(queryResult, params)

    Assert.assertEquals(view.measurement, measurement)
    Assert.assertEquals(view.uuid, params("uuid"))
    Assert.assertEquals(view.range, params("range"))
    Assert.assertEquals(view.aggregation, params("aggregation"))
    Assert.assertEquals(view.shift, params("shift"))

    Assert.assertEquals(view.result.head.cpu, "13.4375")
    Assert.assertEquals(view.result.last.cpu, "13.316666666651145")

    Assert.assertEquals(view.result.length, 3)
  }

  @Test
  def testBrokenPrepareView() = {
    val viewFabric = new CpuViewFabric
    val view: CpuViewMeta = viewFabric.prepareMetricsView(brokenQueryResult, params)

    Assert.assertEquals(view.measurement, measurement)
    Assert.assertEquals(view.uuid, params("uuid"))
    Assert.assertEquals(view.range, params("range"))
    Assert.assertEquals(view.aggregation, params("aggregation"))
    Assert.assertEquals(view.shift, params("shift"))

    Assert.assertEquals(view.result.head.cpu, "")
    Assert.assertEquals(view.result.last.cpu, "13.316666666651145")

    Assert.assertEquals(view.result.length, 3)
  }

}
