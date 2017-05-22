package com.bwsw.pulse.views

import com.paulgoldbaum.influxdbclient.QueryResult


trait View {
  def prepareView(data: QueryResult): CaseClassView = ???
}

trait CaseClassView