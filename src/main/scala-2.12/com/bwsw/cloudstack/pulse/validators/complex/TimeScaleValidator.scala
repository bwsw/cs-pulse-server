package com.bwsw.cloudstack.pulse.validators.complex

import com.bwsw.cloudstack.pulse.config.PulseConfig
import com.bwsw.cloudstack.pulse.validators.Validator
import com.bwsw.cloudstack.pulse.validators.primitive.TimeFrameValidator

/**
  * Created by Ivan Kudryavtsev on 28.07.17.
  */
class TimeScaleValidator extends Validator {

  val rangeKeyword = "range"
  val aggregationKeyword = "aggregation"
  val shiftKeyword = "shift"

  val shiftValidator = new TimeFrameValidator(shiftKeyword)
  val rangeValidator = new TimeFrameValidator(rangeKeyword)
  val aggregationValidator = new TimeFrameValidator(aggregationKeyword)

  override protected def onError(params: Map[String, String]): String = {
    onError(params(rangeKeyword), params(aggregationKeyword), params(shiftKeyword))
  }

  private def onError(range: String, aggregation: String, shift: String) = {
    s"TimeScale parameters [Range '${range}', " +
      s"Aggregation '${aggregation}', Shift: '${shift}'] haven't passed the validation."
  }

  private def validateConfigMatch(range: String, aggregation: String, shift: String): Either[String, String] = {
    val config = PulseConfig()
    config.shifts.filter(s => shift.contains(s)).nonEmpty match {
      case false => Right(onError(range, aggregation, shift))
      case true => config.scales.contains(range) match {
        case false => Right(onError(range, aggregation, shift))
        case true => config.scales(range).aggregations.filter(a => aggregation == a).nonEmpty match {
          case false => Right(onError(range, aggregation, shift))
          case true => Left(s"$range/$aggregation/$shift")
        }
      }
    }
  }

  override def validate(params: Map[String, String]): Either[String, String] = {
    rangeValidator.validate(params) match {
      case Right(error) => Right(error)
      case Left(range) =>
        aggregationValidator.validate(params) match {
          case Right(error) => Right(error)
          case Left(aggregation) =>
            shiftValidator.validate(params) match {
              case Right(error) => Right(error)
              case Left(shift) => validateConfigMatch(range, aggregation, shift)
            }
        }
    }
  }
}
