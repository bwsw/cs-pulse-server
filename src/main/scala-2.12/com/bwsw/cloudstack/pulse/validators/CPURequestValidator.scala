package com.bwsw.cloudstack.pulse.validators

import com.bwsw.cloudstack.pulse.validators.primitive.UUIDValidator

/**
  * Created by Ivan Kudryavtsev on 28.07.17.
  */
class CPURequestValidator extends Validator {
  override protected def onError(params: Map[String, String]): String = ???

  val timeScaleValidator = new TimeScaleValidator()
  val uuidValidator = new UUIDValidator("uuid")
  override def validate(params: Map[String, String]): Either[String, String] = {
    uuidValidator.validate(params) match {
      case Right(error) => Right(error)
      case Left(uuid) => timeScaleValidator.validate(params) match {
        case Right(error) => Right(error)
        case Left(response) => Left(s"$uuid/$response")
      }
    }
  }
}
