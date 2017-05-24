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
  override def prepareQuery(params: Map[String, String]): String = {
    "SELECT DERIVATIVE(MEAN(\"cpuTime\")," + params("aggregation")+") / " +
      "LAST(\"cpus\") / 60 * 100 AS \"cpu\" FROM \"cpuTime\" " +
      "WHERE \"vmUuid\" = '" + params("uuid") + "' AND " +
      "time > now() - " + params("range") + " - " + params("shift") + " AND " +
      "time < now() - " + params("shift") + " GROUP BY time(" + params("aggregation") + ")"
  }
}

class Ram extends Resource {
  override def prepareQuery(params: Map[String, String]): String = {
    "SELECT MEAN(\"rss\") AS \"rss\" FROM \"rss\" " +
      "WHERE \"vmUuid\" = '" + params("uuid") + "' AND " +
      "time > now() - " + params("range") + " - " + params("shift") + " AND " +
      "time < now() - " + params("shift") + " GROUP BY time(" + params("aggregation") + ")"
  }
}

class Disk extends Resource {
  override def prepareQuery(params: Map[String, String]): String = {
    "SELECT MEAN(\"ioErrors\") AS \"ioErrors\", " +
      "MEAN(\"readBytes\") AS \"readBytes\", " +
      "MEAN(\"writeBytes\") AS \"writeBytes\", " +
      "MEAN(\"readIOPS\") AS \"readIOPS\", " +
      "MEAN(\"writeIOPS\") AS \"writeIOPS\" " +
      "FROM \"disk\" " +
      "WHERE \"vmUuid\" = '" + params("uuid") + "' AND " +
      "\"image\" = '" + params("diskUuid") + "' AND " +
      "time > now() - " + params("range") + " - " + params("shift") + " AND " +
      "time < now() - " + params("shift") + " GROUP BY time(" + params("aggregation") + ")"
  }
}

class Network extends Resource {
  override def prepareQuery(params: Map[String, String]): String = {
    "SELECT MEAN(\"ioErrors\") AS \"ioErrors\", " +
      "MEAN(\"readBytes\") AS \"readBytes\", " +
      "MEAN(\"writeBytes\") AS \"writeBytes\", " +
      "MEAN(\"readIOPS\") AS \"readIOPS\", " +
      "MEAN(\"writeIOPS\") AS \"writeIOPS\" " +
      "FROM \"networkInterface\" " +
      "WHERE \"vmUuid\" = '" + params("uuid") + "' AND " +
      "\"mac\" = '" + params("mac") + "' AND " +
      "time > now() - " + params("range") + " - " + params("shift") + " AND " +
      "time < now() - " + params("shift") + " GROUP BY time(" + params("aggregation") + ")"
  }
}
