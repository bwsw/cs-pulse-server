package com.bwsw.pulse.validators


abstract class Validator {
  val fieldName: String
  val message: String

  def validate(params: Map[String, String]): (List[String], Boolean) = {
    val isValid = specValidate(params)
    isValid match {
      case true => (List(), isValid)
      case false => (List(message), isValid)
    }
  }

  protected def specValidate(params: Map[String, String]): Boolean
}

/**
  * Used as common validator.
  * @param validator
  */
abstract class ValidationDecorator(validator: Validator) extends Validator {
  override val fieldName = validator.fieldName

  override def validate(params: Map[String, String]): (List[String], Boolean) = {
    val specIsValid = specValidate(params)
    val (errors, isValid) = validator.validate(params)
    specIsValid match {
      case true => (errors, specIsValid && isValid)
      case false => (List(message) ::: errors, specIsValid && isValid)
    }
  }
}

/**
  * Validators included list of validators.
  * @param validators list of Validator
  */
class Validators(validators: List[Validator]) extends Validator {
  override val fieldName = ""
  override val message = ""

  override def validate(params: Map[String, String]): (List[String], Boolean) = {
    validators.foldLeft(List[String](), true)((acc, validator) => {
      val (errors, isValid) = validator.validate(params)
      (acc._1.:::(errors), acc._2 && isValid)
    })
  }

  override def specValidate(params: Map[String, String]): Boolean = ???
}