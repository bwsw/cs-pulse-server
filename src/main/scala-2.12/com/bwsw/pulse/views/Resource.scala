package com.bwsw.pulse.views


case class CpuViewMeta(measurement: String,
                       uuid: String,
                       range: String,
                       aggregation: String,
                       shift: String,
                       result: scala.collection.mutable.ArrayBuffer[CpuViewData]) extends View
case class CpuViewData(cpu: String) extends View


case class RamViewMeta(measurement: String,
                       uuid: String,
                       range: String,
                       aggregation: String,
                       shift: String,
                       result: scala.collection.mutable.ArrayBuffer[RamViewData]) extends View
case class RamViewData(rss: String) extends View


case class DiskViewMeta(measurement: String,
                        uuid: String,
                        range: String,
                        aggregation: String,
                        shift: String,
                        diskUuid: String,
                        result: scala.collection.mutable.ArrayBuffer[DiskViewData]) extends View
case class DiskViewData(ioErrors: String,
                        readBytes: String,
                        writeBytes: String,
                        readIOPS: String,
                        writeIOPS: String) extends View


case class NetworkViewMeta(measurement: String,
                           uuid: String,
                           range: String,
                           aggregation: String,
                           shift: String,
                           mac: String,
                           result: scala.collection.mutable.ArrayBuffer[NetworkViewData]) extends View
case class NetworkViewData(readBytes: String,
                           writeBytes: String,
                           readErrors: String,
                           writeErrors: String,
                           readDrops: String,
                           writeDrops: String,
                           readPackets: String,
                           writePackets: String) extends View