package com.bwsw.cloudstack.pulse.influx

object GaugeField {
  def apply(name: String, modifier: String = "") = new GaugeField(name, modifier)
}
class GaugeField(name: String, modifier: String) extends Field {
  override def toString(): String = {
    s"""MEAN("$name")$modifier"""
  }
}