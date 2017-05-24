package com.bwsw.pulse.views

import org.influxdb.dto.QueryResult


trait View {
  def prepareView(data: QueryResult): CaseClassView = ???
}

trait CaseClassView