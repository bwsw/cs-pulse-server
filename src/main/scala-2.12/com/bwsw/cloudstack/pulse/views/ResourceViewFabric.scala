package com.bwsw.cloudstack.pulse.views

import com.bwsw.cloudstack.pulse.models.InfluxTable
import org.influxdb.dto.QueryResult

import scala.collection.JavaConverters._


abstract class MetricsViewFabric(table: InfluxTable) extends ViewFabric {
  def getTable = table

  def transformToMap(sourceData: QueryResult): Seq[Map[String, String]] = {
    val cols = getSeries(sourceData).getColumns.asScala
    getSeries(sourceData).getValues.asScala
      .map {
        value => (0 until cols.size)
          .filter(cols(_) != "time")
          .map(index => cols(index) -> Math.round(getValue(value, index).toFloat).toString).toMap
      }
  }
}

class CpuViewFabric(table: InfluxTable) extends MetricsViewFabric(table) {
  override def prepareMetricsView(sourceData: QueryResult, params: Map[String, String]): CpuViewMeta = {

    CpuViewMeta(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      result = transformToMap(sourceData))
  }
}


class RamViewFabric(table: InfluxTable) extends MetricsViewFabric(table) {
  override def prepareMetricsView(sourceData: QueryResult, params: Map[String, String]): RamViewMeta = {
    RamViewMeta(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      result = transformToMap(sourceData))
  }
}


class DiskViewFabric(table: InfluxTable) extends MetricsViewFabric(table) {
  override def prepareMetricsView(sourceData: QueryResult, params: Map[String, String]): DiskViewMeta = {
    DiskViewMeta(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      diskUuid = params("diskUuid"),
      result = transformToMap(sourceData))
  }
}


class NetworkViewFabric(table: InfluxTable) extends MetricsViewFabric(table) {
  override def prepareMetricsView(sourceData: QueryResult, params: Map[String, String]): NetworkViewMeta = {
    NetworkViewMeta(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      mac = params("mac"),
      result = transformToMap(sourceData))
  }
}
