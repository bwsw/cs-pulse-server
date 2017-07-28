package com.bwsw.cloudstack.pulse.influx

object CounterField {
  def apply(name: String, aggregation: String, modifier: String = "") = new CounterField(name, aggregation, modifier)
  private[pulse] def transformAggregationToSeconds(value: String) = {
    val Pattern = "^([0-9]+)([mhdw])$".r
    value match {
      case Pattern(number, scale) => scale match {
        case "w" => number.toInt * 3600 * 24 * 7
        case "d" => number.toInt * 3600 * 24
        case "h" => number.toInt * 3600
        case "m" => number.toInt * 60
      }
    }
  }
}

class CounterField(name: String, aggregation: String, modifier: String) extends Field {

  val aggregationSeconds = CounterField.transformAggregationToSeconds(aggregation)

  override def toString() = {
    s"""NON_NEGATIVE_DERIVATIVE(MEAN("$name"), """ + aggregation + s")$modifier / "  + aggregationSeconds
  }
}
