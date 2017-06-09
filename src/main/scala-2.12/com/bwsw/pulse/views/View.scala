package com.bwsw.pulse.views

import org.influxdb.dto.QueryResult
import scala.collection.JavaConverters._


trait ViewFabric {
  def prepareView(sourceData: QueryResult, params: Map[String, String]): View = {
    val error = checkQueryResult(sourceData)
    if (error.isDefined) return ErrorView(params, List(error.get))
    prepareSpecView(sourceData, params)
  }

  def prepareSpecView(sourceData: QueryResult, params: Map[String, String]): View

  def checkQueryResult(sourceData: QueryResult): Option[String] = {
    if (sourceData.hasError) {
      Some(sourceData.getError)
    }
    else if (sourceData.getResults.asScala.head.hasError) {
      Some(sourceData.getResults.asScala.head.getError)
    }
    else None
  }

  def getValue(lst: java.util.List[AnyRef], ind: Int): String = {
    var t = lst.asScala.toList.lift(ind)
    if (t.contains(null)) t = Some("")
    t.get.toString
  }

}

trait View