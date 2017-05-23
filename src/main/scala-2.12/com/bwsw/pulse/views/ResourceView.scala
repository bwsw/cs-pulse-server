package com.bwsw.pulse.views

import org.influxdb.dto.QueryResult


class CpuView extends View {
  override def prepareView(data: QueryResult): CaseClassView = ???
}

class DiskView extends View {
  override def prepareView(data: QueryResult): CaseClassView = ???
}

class RamView extends View {
  override def prepareView(data: QueryResult): CaseClassView = ???
}

class NetworkView extends View {
  override def prepareView(data: QueryResult): CaseClassView = ???
}
