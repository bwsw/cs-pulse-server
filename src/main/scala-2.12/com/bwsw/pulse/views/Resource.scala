package com.bwsw.pulse.views


case class CpuViewMeta(measurement: String,
                       uuid: String,
                       range: String,
                       aggregation: String,
                       shift: String,
                       result: scala.collection.mutable.ArrayBuffer[CpuViewData]) extends View
case class CpuViewData(cpu: String) extends View
