package com.bwsw.cloudstack.pulse.validators

/**
  * Created by Ivan Kudryavtsev on 28.07.17.
  */


abstract class Validator {
  protected def onError(params: Map[String, String]): String
  def validate(params: Map[String, String]): Either[String, String]
}

class PrimitiveValidator(field: String) extends Validator {
  def fieldName = field
  override protected def onError(params: Map[String, String]): String = ""
  override def validate(params: Map[String, String]): Either[String, String] = Left("")
}

