package com.bwsw.cloudstack.pulse.validators

import scala.util.{Failure, Success, Try}

/**
  * Created by Ivan Kudryavtsev on 28.07.17.
  */

class PatternValidator(field: String) extends PrimitiveValidator(field) {
  protected def getPattern = "".r

  override def validate(params: Map[String, String]): Either[String, String] = {
    val Pattern = getPattern
    Try(params(fieldName) match {
      case Pattern(value) => value
    }) match {
      case Success(value) => Left(value)
      case Failure(ex) => Right(onError(params))
    }
  }

  override protected def onError(params: Map[String, String]): String = {
    if(params.contains(fieldName))
      s"Field '$fieldName' haven't passed validation routine. Expected: '${getPattern.toString()}', Got: '${params(fieldName)}'."
    else
      s"Field '$fieldName' haven't been found in parameters."
  }

}
