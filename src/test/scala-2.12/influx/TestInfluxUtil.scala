package influx

import java.util

import com.bwsw.cloudstack.pulse.influx.InfluxService
import org.influxdb.InfluxDB
import org.influxdb.dto._
import org.mockito.Mockito
import scala.collection.JavaConverters._

class TestInfluxUtil {

//  val host = "localhost"
//  val port = "8086"
//  val username = "username"
//  val password = "password"
//  val database = "database"
//
//  var queryResult: QueryResult = _
//
//
//  @Before
//  def initialize() = {
//    val values: util.List[util.List[AnyRef]] =
//      List (
//        List (
//          "foo".asInstanceOf[AnyRef],
//          "50".asInstanceOf[AnyRef]
//        ).asJava,
//        List (
//          "bar".asInstanceOf[AnyRef],
//          "60".asInstanceOf[AnyRef]
//        ).asJava
//      ).asJava
//
//    val series = new QueryResult.Series
//    series.setColumns(List("name", "cpu").asJava)
//    series.setName("cpu")
//    series.setValues(values)
//
//    val result = new QueryResult.Result
//    result.setSeries(List(series).asJava)
//
//    queryResult = new QueryResult
//    queryResult.setResults(List(result).asJava)
//
//  }
//
//  @Test
//  def testCreateConnection() = {
//    InfluxService.connect(host, port, username, password, database)
//    Assert.assertEquals(database, InfluxService.dbName)
//    Assert.assertEquals(InfluxService.influxDB.isInstanceOf[InfluxDB], true)
//  }
//
//  @Test
//  def testExecuteQuery() = {
//    val mockInfluxDB = Mockito.mock(classOf[InfluxDB])
//    Mockito.when(mockInfluxDB.query(new Query("SELECT * FROM cpu", database))).thenReturn(queryResult)
//
//    InfluxService.influxDB = mockInfluxDB
//    InfluxService.dbName = database
//
//    val sourceData = InfluxService.query("SELECT * FROM cpu")
//
//    Assert.assertEquals(sourceData.getResults.get(0).getSeries.get(0).getName, "cpu")
//
//    Assert.assertEquals(sourceData.getResults.get(0).getSeries.get(0).getColumns,
//      List("name", "cpu").asJava)
//
//    Assert.assertEquals(sourceData.getResults.get(0).getSeries.get(0).getValues,
//      List(List("foo", "50").asJava, List("bar", "60").asJava).asJava)
//  }
}


