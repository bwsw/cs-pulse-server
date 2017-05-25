package views

import java.util

import com.bwsw.pulse.views.{CpuViewFabric, CpuViewMeta}
import org.junit._
import org.influxdb.dto._

import scala.collection.JavaConverters._

class TestCpuView {

  val database = "database"

  var queryResult: QueryResult = _
  var params: Map[String, String] = _

  var errorQueryResult: QueryResult = _
  var errorParams: Map[String, String] = _

  val measurement = "cpuTime"

  @Before
  def initialize() = {
    params = Map(
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
  }


  @Test
  def testPrepareView() = {
    val cpuFabric = new CpuViewFabric
    val view: CpuViewMeta = cpuFabric.prepareView(queryResult, params)

    Assert.assertEquals(view.measurement, measurement)
    Assert.assertEquals(view.uuid, params("uuid"))
    Assert.assertEquals(view.range, params("range"))
    Assert.assertEquals(view.aggregation, params("aggregation"))
    Assert.assertEquals(view.shift, params("shift"))

    Assert.assertEquals(view.result.head.cpu, "13.4375")
    Assert.assertEquals(view.result.last.cpu, "13.316666666651145")
  }

}
