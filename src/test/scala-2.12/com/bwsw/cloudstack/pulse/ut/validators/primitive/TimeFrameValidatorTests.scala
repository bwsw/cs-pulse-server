package com.bwsw.cloudstack.pulse.ut.validators.primitive

import com.bwsw.cloudstack.pulse.validators.primitive.TimeFrameValidator
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by ivan on 28.07.17.
  */
class TimeFrameValidatorTests extends FlatSpec with Matchers {
  it should "validate uuids properly" in {
    val v = new TimeFrameValidator("test")
    v.validate(Map("test" -> "1h")) shouldBe Left("1h")
    v.validate(Map("test" -> "1m")) shouldBe Left("1m")
    v.validate(Map("test" -> "1d")) shouldBe Left("1d")
  }

  it should "validate wrong data properly" in {
    val v = new TimeFrameValidator("test")
    val validationResult = v.validate(Map("test" -> "550e8400-e29b-41d4-a716-446655440000a")) match {
      case Right(_) => true
      case Left(_) => false
    }
    validationResult shouldBe true
  }

  it should "validate missing data properly" in {
    val v = new TimeFrameValidator("test")
    val validationResult = v.validate(Map("test2" -> "550e8400-e29b-41d4-a716-446655440000a")) match {
      case Right(msg) => true
      case Left(_) => false
    }
    validationResult shouldBe true
  }

}