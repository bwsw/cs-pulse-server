package com.bwsw.pulse.validators


class TimeValidator(validator: Validator) extends ValidationDecorator(validator) {
  val message = s"Argument $fieldName must be in time format"

  override def specValidate(params: Map[String, String]): Boolean = {
    params(fieldName).matches("\\d*[smhd]")
  }
}

class UuidValidator(validator: Validator) extends ValidationDecorator(validator) {
  override val message = s"Argument $fieldName must be uuid"

  override def specValidate(params: Map[String, String]): Boolean = {
    params(fieldName).matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
  }
}

class NullValidator(validator: Validator) extends ValidationDecorator(validator) {
  val message = s"Argument $fieldName must not be empty"

  override def specValidate(params: Map[String, String]): Boolean = {
    var res: Boolean = true
    params.keys.toList.contains(fieldName) match {
      case true => if (params(fieldName) == null) res = false
      case false => res = false
    }
    res
  }
}

