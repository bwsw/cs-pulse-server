package com.bwsw.pulse.models

import com.bwsw.pulse.influx.InfluxUtil
import org.influxdb.dto.QueryResult


abstract class Resource {
  def getResult(params: Map[String, String]): QueryResult = {
    val query: String = prepareQuery(params)
    InfluxUtil.executeQuery(query)
  }

  def prepareQuery(params: Map[String, String]): String
}


class Cpu extends Resource {
  override def prepareQuery(params: Map[String, String]): String = ???
}

class Ram extends Resource {
  override def prepareQuery(params: Map[String, String]): String = ???
}

class Disk extends Resource {
  override def prepareQuery(params: Map[String, String]): String = ???
}

class Network extends Resource {
  override def prepareQuery(params: Map[String, String]): String = ???
}

