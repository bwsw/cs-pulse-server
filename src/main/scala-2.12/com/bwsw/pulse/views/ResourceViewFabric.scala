package com.bwsw.pulse.views

import org.influxdb.dto.QueryResult
import scala.collection.JavaConverters._
import scala.collection.mutable


class CpuViewFabric extends ViewFabric {
  override def prepareSpecView(sourceData: QueryResult, params: Map[String, String]): CpuViewMeta = {
    val data: mutable.ArrayBuffer[CpuViewData] = mutable.ArrayBuffer()

    val series = sourceData.getResults.asScala.head.getSeries.asScala.head
    series.getValues.forEach(v => data.append(CpuViewData(getValue(v, 1))))

    CpuViewMeta(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      result = data)
  }
}


class RamViewFabric extends ViewFabric {
  override def prepareSpecView(sourceData: QueryResult, params: Map[String, String]): RamViewMeta = {
    val data: mutable.ArrayBuffer[RamViewData] = mutable.ArrayBuffer()

    val series = sourceData.getResults.asScala.head.getSeries.asScala.head
    series.getValues.forEach(v => data.append(RamViewData(getValue(v, 1))))

    RamViewMeta(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      result = data)
  }
}


class DiskViewFabric extends ViewFabric {
  override def prepareSpecView(sourceData: QueryResult, params: Map[String, String]): DiskViewMeta = {
    val data: mutable.ArrayBuffer[DiskViewData] = mutable.ArrayBuffer()

    val series = sourceData.getResults.asScala.head.getSeries.asScala.head
    series.getValues.forEach(v => data.append(
      DiskViewData(
        getValue(v, 1),
        getValue(v, 2),
        getValue(v, 3),
        getValue(v, 4),
        getValue(v, 5))))

    DiskViewMeta(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      diskUuid = params("diskUuid"),
      result = data)
  }
}


class NetworkViewFabric extends ViewFabric {
  override def prepareSpecView(sourceData: QueryResult, params: Map[String, String]): NetworkViewMeta = {
    val data: mutable.ArrayBuffer[NetworkViewData] = mutable.ArrayBuffer()

    val series = sourceData.getResults.asScala.head.getSeries.asScala.head
    series.getValues.forEach(v => data.append(
      NetworkViewData(
        getValue(v, 1),
        getValue(v, 2),
        getValue(v, 3),
        getValue(v, 4),
        getValue(v, 5),
        getValue(v, 6),
        getValue(v, 7),
        getValue(v, 8))))

    NetworkViewMeta(
      uuid = params("uuid"),
      range = params("range"),
      aggregation = params("aggregation"),
      shift = params("shift"),
      mac = params("mac"),
      result = data)
  }
}
