package com.bwsw.pulse.validators


class TimeFormatValidator(validator: Validator) extends ValidationDecorator(validator) {
  val message = s"Argument $fieldName must be in influx time format, with suffix."

  override def specValidate(params: Map[String, String]): Boolean = {
    params(fieldName).matches("\\d*[smhwd]")
  }
}

class UuidValidator(validator: Validator) extends ValidationDecorator(validator) {
  override val message = s"Argument $fieldName must be in UUID format."

  override def specValidate(params: Map[String, String]): Boolean = {
    params(fieldName).matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
  }
}

class NullValidator(validator: Validator) extends ValidationDecorator(validator) {
  val message = s"Argument $fieldName must not be empty."

  override def specValidate(params: Map[String, String]): Boolean = {
    var res: Boolean = true
    params.keys.toList.contains(fieldName) match {
      case true => if (params(fieldName) == null) res = false
      case false => res = false
    }
    res
  }
}

class AggregationRangeValidator(validator: Validator) extends ValidationDecorator(validator) {
  val rangeValidator = new RangeValidator
  val message = s"Argument $fieldName: ${rangeValidator.fieldName} argument must be specified firstly."

  override def specValidate(params: Map[String, String]): Boolean = {
    rangeValidator.validate(params)._2
  }
}
