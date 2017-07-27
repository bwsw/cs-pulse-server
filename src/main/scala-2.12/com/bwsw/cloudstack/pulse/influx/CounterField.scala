package com.bwsw.cloudstack.pulse.influx

object CounterField {
  def apply(name: String, aggregation: String, modifier: String = "") = new CounterField(name, aggregation, modifier)
}




class CounterField(name: String, aggregation: String, modifier: String) extends Field {
  private def transformAggregationToSeconds(value: String) = {
    val Pattern = "([0-9]+)([mhd])".r
    value match {
      case Pattern(number, scale) => scale match {
        case "d" => number.toInt * 3600 * 24
        case "h" => number.toInt * 3600
        case "m" => number.toInt * 60
      }
    }
  }

  val aggregationSeconds = transformAggregationToSeconds(aggregation)

  override def toString() = {
    s"""NON_NEGATIVE_DERIVATIVE(MEAN("$name"), """ + aggregation + s")$modifier / "  + aggregationSeconds
  }
}
