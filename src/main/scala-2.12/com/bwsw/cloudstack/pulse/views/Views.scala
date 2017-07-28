package com.bwsw.cloudstack.pulse.views

trait View

case class CPUView(measurement: String = "cputime",
                   uuid: String,
                   range: String,
                   aggregation: String,
                   shift: String,
                   result: Seq[Map[String, String]]) extends View

case class RAMView(measurement: String = "ram",
                   uuid: String,
                   range: String,
                   aggregation: String,
                   shift: String,
                   result: Seq[Map[String, String]]) extends View

case class DiskView(measurement: String = "disk",
                    uuid: String,
                    range: String,
                    aggregation: String,
                    shift: String,
                    diskUuid: String,
                    result: Seq[Map[String, String]]) extends View

case class NetworkView(measurement: String = "network",
                       uuid: String,
                       range: String,
                       aggregation: String,
                       shift: String,
                       mac: String,
                       result: Seq[Map[String, String]]) extends View

