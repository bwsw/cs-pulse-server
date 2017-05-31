package com.bwsw.pulse.validators


class VmUuidValidator extends Validator {
  val fieldName = "uuid"
  val message = s"Argument $fieldName exception"

  override def specValidate(params: Map[String, String]): Boolean = {
    true
  }
}

class RangeValidator extends Validator {
  val fieldName = "range"
  val message = s"Argument $fieldName not included allowed interval"

  override def specValidate(params: Map[String, String]): Boolean = {
    //TODO implement validator related to config values.
    true
  }
}

class AggregationValidator extends Validator {
  val fieldName = "aggregation"
  val message = s"Argument $fieldName not included allowed interval"

  override def specValidate(params: Map[String, String]): Boolean = {
    //TODO implement validator related to config values.
    true
  }
}

class ShiftValidator extends Validator {
  val fieldName = "shift"
  val message = s"Argument $fieldName exception"

  override def specValidate(params: Map[String, String]): Boolean = {
    //TODO implement validator related to config values.
    true
  }
}

class MacValidator extends Validator {
  val fieldName = "mac"
  val message = s"Argument $fieldName not matches"

  override def specValidate(params: Map[String, String]): Boolean = {
    params(fieldName).matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")
  }
}

class DiskValidator extends Validator {
  val fieldName = "diskUuid"
  val message = s"Argument $fieldName exception"

  override def specValidate(params: Map[String, String]): Boolean = {
    true
  }
}
