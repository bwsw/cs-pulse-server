package com.bwsw.cloudstack.pulse.validators

import com.bwsw.cloudstack.pulse.config.PulseConfig


class VmUuidValidator extends Validator {
  val fieldName = "uuid"
  val message = s"Argument $fieldName exception"

  override def specValidate(params: Map[String, String]): Boolean = {
    true
  }
}

class RangeValidator extends Validator {
  val fieldName = "range"
  val message = s"Argument $fieldName not included into allowed interval. See configuration file."

  override def specValidate(params: Map[String, String]): Boolean = {
    PulseConfig.ranges.contains(params(fieldName))
  }
}

class AggregationValidator extends Validator {
  val fieldName = "aggregation"
  val message = s"Argument $fieldName not included into allowed interval. See configuration file."

  override def specValidate(params: Map[String, String]): Boolean = {
    val range_config = PulseConfig.scales.filter(rangeCFG => rangeCFG.range == params("range")).head
    range_config.aggregation.contains(params(fieldName))
  }
}

class ShiftValidator extends Validator {
  val fieldName = "shift"
  val message = s"Argument $fieldName not included into allowed interval. See configuration file."

  override def specValidate(params: Map[String, String]): Boolean = {
    PulseConfig.shifts.contains(params(fieldName).last.toString)
  }
}

class MacValidator extends Validator {
  val fieldName = "mac"
  val message = s"Argument $fieldName not matches."

  override def specValidate(params: Map[String, String]): Boolean = {
    params(fieldName).matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")
  }
}

class DiskValidator extends Validator {
  val fieldName = "diskUuid"
  val message = s"Argument $fieldName exception."

  override def specValidate(params: Map[String, String]): Boolean = {
    true
  }
}
