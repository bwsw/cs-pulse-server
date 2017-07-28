package com.bwsw.cloudstack.pulse.validators

/**
  * Created by ivan on 28.07.17.
  */
class Validator(field: String) {
  def fieldName = field
  protected def onError(params: Map[String, String]): String = ""
  def validate(params: Map[String, String]): Either[String, String] = Left("")

}
