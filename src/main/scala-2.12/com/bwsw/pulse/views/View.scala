package com.bwsw.pulse.views

import org.influxdb.dto.QueryResult
import scala.collection.JavaConverters._


trait ViewFabric {
  def prepareView(sourceData: QueryResult, params: Map[String, String]): (Boolean, View) = {
    val error = checkQueryResult(sourceData)
    if (error.isDefined) return (false, ErrorView(params, List(error.get)))
    (true, prepareSpecView(sourceData, params))
  }

  protected def prepareSpecView(sourceData: QueryResult, params: Map[String, String]): View

  protected def checkQueryResult(sourceData: QueryResult): Option[String] = {
    if (sourceData.hasError) {
      Some(sourceData.getError)
    }
    else if (sourceData.getResults.asScala.head.hasError) {
      Some(sourceData.getResults.asScala.head.getError)
    }
    else None
  }

  protected def getValue(lst: java.util.List[AnyRef], ind: Int): String = {
    var t = lst.asScala.toList.lift(ind)
    if (t.contains(null)) t = Some("")
    t.get.toString
  }

  protected def get_series(sourceData: QueryResult): QueryResult.Series = {
    val emptySeries = new QueryResult.Series
    emptySeries.setValues(List().asJava)
    if (sourceData.getResults.asScala.head.getSeries == null) emptySeries else sourceData.getResults.asScala.head.getSeries.asScala.head
  }

}

trait View