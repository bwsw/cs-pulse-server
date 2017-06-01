package com.bwsw.pulse.views

import org.influxdb.dto.QueryResult
import scala.collection.JavaConverters._


trait ViewFabric {
  def prepareView(sourceData: QueryResult, params: Map[String, String]): View

  def getValue(lst: java.util.List[AnyRef], ind: Int): String = {
    var t = lst.asScala.toList.lift(ind)
    if (t.contains(null)) t = Some("")
    t.get.toString
  }

}

trait View