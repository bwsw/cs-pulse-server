package com.bwsw.pulse.views

import org.influxdb.dto.QueryResult
import scala.collection.JavaConverters._
import scala.collection.mutable


class CpuViewFabric extends ViewFabric {
  override def prepareView(sourceData: QueryResult, params: Map[String, String]): CpuViewMeta = {
    val data: mutable.ArrayBuffer[CpuViewData] = mutable.ArrayBuffer()

    val series = sourceData.getResults.asScala.head.getSeries.asScala.head
    series.getValues.forEach(v => data.append(CpuViewData(v.get(1).toString)))

    CpuViewMeta(series.getName, params("uuid"), params("range"), params("aggregation"), params("shift"), data)
  }
}


class RamViewFabric extends ViewFabric {
  override def prepareView(sourceData: QueryResult, params: Map[String, String]): RamViewMeta = {
    val data: mutable.ArrayBuffer[RamViewData] = mutable.ArrayBuffer()

    val series = sourceData.getResults.asScala.head.getSeries.asScala.head
    series.getValues.forEach(v => data.append(RamViewData(v.get(1).toString)))

    RamViewMeta(series.getName, params("uuid"), params("range"), params("aggregation"), params("shift"), data)
  }
}


class DiskViewFabric extends ViewFabric {
  override def prepareView(sourceData: QueryResult, params: Map[String, String]): DiskViewMeta = {
    val data: mutable.ArrayBuffer[DiskViewData] = mutable.ArrayBuffer()

    val series = sourceData.getResults.asScala.head.getSeries.asScala.head
    series.getValues.forEach(v => data.append(
      DiskViewData(
        v.get(1).toString,
        v.get(2).toString,
        v.get(3).toString,
        v.get(4).toString,
        v.get(5).toString)))

    DiskViewMeta(
      series.getName,
      params("uuid"),
      params("range"),
      params("aggregation"),
      params("shift"),
      params("diskUuid"),
      data)
  }
}


class NetworkViewFabric extends ViewFabric {
  override def prepareView(sourceData: QueryResult, params: Map[String, String]): NetworkViewMeta = {
    val data: mutable.ArrayBuffer[NetworkViewData] = mutable.ArrayBuffer()

    val series = sourceData.getResults.asScala.head.getSeries.asScala.head
    series.getValues.forEach(v => data.append(
      NetworkViewData(
        v.get(1).toString,
        v.get(2).toString,
        v.get(3).toString,
        v.get(4).toString,
        v.get(5).toString,
        v.get(6).toString,
        v.get(7).toString,
        v.get(8).toString)))

    NetworkViewMeta(
      series.getName,
      params("uuid"),
      params("range"),
      params("aggregation"),
      params("shift"),
      params("mac"),
      data)
  }
}
