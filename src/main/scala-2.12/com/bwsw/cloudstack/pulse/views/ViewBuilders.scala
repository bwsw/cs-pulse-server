package com.bwsw.cloudstack.pulse.views

import com.bwsw.cloudstack.pulse.models.InfluxModel
import org.influxdb.dto.QueryResult

import scala.collection.JavaConverters._
import scala.util.{Success, Try}

trait ViewBuilder {
  def prepareView(queryResult: QueryResult, params: Map[String, String]): (Boolean, View) = {
    val error = checkQueryResult(queryResult)
    if (error.isDefined)
      (false, ErrorView(params, List(error.get)))
    else
      (true, prepareMetricsView(queryResult, params))
  }

  protected def prepareMetricsView(queryResult: QueryResult, params: Map[String, String]): View

  protected def checkQueryResult(queryResult: QueryResult): Option[String] = {
    if (queryResult.hasError) {
      Some(queryResult.getError)
    }
    else if (queryResult.getResults.asScala.head.hasError) {
      Some(queryResult.getResults.asScala.head.getError)
    }
    else None
  }

  protected def getValue(list: java.util.List[AnyRef], index: Int): String = { //todo: fixit anyref
  var t = list.asScala.toList.lift(index)
    if (t.contains(null)) t = Some("")
    t.get.toString
  }

  protected def getSeries(sourceData: QueryResult): QueryResult.Series = {
    val emptySeries = new QueryResult.Series
    emptySeries.setValues(List().asJava)
    if (sourceData.getResults.asScala.head.getSeries == null) emptySeries else sourceData.getResults.asScala.head.getSeries.asScala.head
  }

}

abstract class MetricsViewBuilder(table: InfluxModel) extends ViewBuilder {
  def getTable = table

  def transformToMap(sourceData: QueryResult): Seq[Map[String, String]] = {
    val cols = getSeries(sourceData).getColumns.asScala
    getSeries(sourceData).getValues.asScala
      .map {
        value => (0 until cols.size)
          .map(index => cols(index) -> {
            val v = getValue(value, index)
              if(cols(index) != "time") {
                Try(Math.round(v.toFloat).toString) match {
                  case Success(res) => res
                  case _ => v
                }
              }
              else
                v
          }).toMap
      }
  }
}

class CpuViewBuilder(model: InfluxModel) extends MetricsViewBuilder(model) {
  override def prepareMetricsView(sourceData: QueryResult, params: Map[String, String]): CPUView = {
    CPUView(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      result = transformToMap(sourceData))
  }
}


class RamViewBuilder(model: InfluxModel) extends MetricsViewBuilder(model) {
  override def prepareMetricsView(sourceData: QueryResult, params: Map[String, String]): RAMView = {
    RAMView(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      result = transformToMap(sourceData))
  }
}


class DiskViewBuilder(model: InfluxModel) extends MetricsViewBuilder(model) {
  override def prepareMetricsView(sourceData: QueryResult, params: Map[String, String]): DiskView = {
    DiskView(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      diskUuid = params("diskUuid"),
      result = transformToMap(sourceData))
  }
}


class NetworkViewBuilder(model: InfluxModel) extends MetricsViewBuilder(model) {
  override def prepareMetricsView(sourceData: QueryResult, params: Map[String, String]): NetworkView = {
    NetworkView(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      mac = params("mac"),
      result = transformToMap(sourceData))
  }
}

