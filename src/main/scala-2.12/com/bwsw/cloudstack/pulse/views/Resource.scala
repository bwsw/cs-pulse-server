package com.bwsw.cloudstack.pulse.views


case class CpuViewMeta(measurement: String = "cputime",
                       uuid: String,
                       range: String,
                       aggregation: String,
                       shift: String,
                       result: Seq[Map[String, String]]) extends View

case class RamViewMeta(measurement: String = "ram",
                       uuid: String,
                       range: String,
                       aggregation: String,
                       shift: String,
                       result: Seq[Map[String, String]]) extends View

case class DiskViewMeta(measurement: String = "disk",
                        uuid: String,
                        range: String,
                        aggregation: String,
                        shift: String,
                        diskUuid: String,
                        result: Seq[Map[String, String]]) extends View

case class NetworkViewMeta(measurement: String = "network",
                           uuid: String,
                           range: String,
                           aggregation: String,
                           shift: String,
                           mac: String,
                           result: Seq[Map[String, String]]) extends View

