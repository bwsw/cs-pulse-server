package com.bwsw.pulse.views

import org.influxdb.dto.QueryResult


trait ViewFabric {
  def prepareView(sourceData: QueryResult, params: Map[String, String]): View
}

trait View