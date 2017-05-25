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

class DiskViewFabric extends ViewFabric {
  override def prepareView(sourceData: QueryResult, params: Map[String, String]): View = ???
}

class RamViewFabric extends ViewFabric {
  override def prepareView(sourceData: QueryResult, params: Map[String, String]): View = ???
}

class NetworkViewFabric extends ViewFabric {
  override def prepareView(sourceData: QueryResult, params: Map[String, String]): View = ???
}
