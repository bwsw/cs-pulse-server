package influx

import java.util
import java.util.concurrent.{ThreadFactory, TimeUnit}

import org.influxdb.InfluxDB
import org.influxdb.InfluxDB.{ConsistencyLevel, LogLevel}
import org.influxdb.dto._
import scala.collection.JavaConverters._


class MockInfluxDB extends InfluxDB {
  override def disableBatch(): Unit = ???

  override def enableBatch(actions: Int, flushDuration: Int, flushDurationTimeUnit: TimeUnit): InfluxDB = ???

  override def enableBatch(actions: Int, flushDuration: Int, flushDurationTimeUnit: TimeUnit, threadFactory: ThreadFactory): InfluxDB = ???

  override def disableGzip(): InfluxDB = ???

  override def describeDatabases(): util.List[String] = ???

  override def createDatabase(name: String): Unit = ???

  override def isGzipEnabled: Boolean = ???

  override def write(database: String, retentionPolicy: String, point: Point): Unit = ???

  override def write(udpPort: Int, point: Point): Unit = ???

  override def write(batchPoints: BatchPoints): Unit = ???

  override def write(database: String, retentionPolicy: String, consistency: ConsistencyLevel, records: String): Unit = ???

  override def write(database: String, retentionPolicy: String, consistency: ConsistencyLevel, records: util.List[String]): Unit = ???

  override def write(udpPort: Int, records: String): Unit = ???

  override def write(udpPort: Int, records: util.List[String]): Unit = ???

  override def enableGzip(): InfluxDB = ???

  override def isBatchEnabled: Boolean = ???

  override def close(): Unit = ???

  override def ping(): Pong = ???

  override def deleteDatabase(name: String): Unit = ???

  override def setLogLevel(logLevel: LogLevel): InfluxDB = ???

  override def query(query: Query): QueryResult = {
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

    val queryResult = new QueryResult

    queryResult
  }

  override def query(query: Query, timeUnit: TimeUnit): QueryResult = ???

  override def version(): String = ???
}
