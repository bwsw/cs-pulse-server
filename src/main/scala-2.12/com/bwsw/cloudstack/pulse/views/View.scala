package com.bwsw.cloudstack.pulse.views

import org.influxdb.dto.QueryResult
import scala.collection.JavaConverters._


trait ViewFabric {
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

trait View