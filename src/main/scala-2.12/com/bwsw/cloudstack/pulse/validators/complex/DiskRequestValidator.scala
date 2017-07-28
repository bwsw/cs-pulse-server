package com.bwsw.cloudstack.pulse.validators.complex

import com.bwsw.cloudstack.pulse.validators.Validator
import com.bwsw.cloudstack.pulse.validators.primitive.{MACValidator, UUIDValidator}

/**
  * Created by ivan on 28.07.17.
  */
class DiskRequestValidator extends Validator {

  override protected def onError(params: Map[String, String]): String = ""

  val timeScaleValidator = new TimeScaleValidator()
  val uuidValidator = new UUIDValidator("uuid")
  val vmUuidValidator = new UUIDValidator("vmUuid")

  override def validate(params: Map[String, String]): Either[String, String] = {
    uuidValidator.validate(params) match {
      case Right(error) => Right(error)
      case Left(uuid) => vmUuidValidator.validate(params) match {
        case Right(error) => Right(error)
        case Left(vmUuid) => timeScaleValidator.validate(params) match {
          case Right(error) => Right(error)
          case Left(response) => Left(s"$uuid/$vmUuid/$response")
        }
      }
    }
  }

}
