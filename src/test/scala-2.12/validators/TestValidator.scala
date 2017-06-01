package validators

import org.junit._
import com.bwsw.pulse.validators._


class TestValidator {

  var params: Map[String, String] = _
  var brokenParams: Map[String, String] = _

  @Before
  def initialize() = {
    params = Map(
      "uuid" -> "c60e3462-a60c-4f8d-9d2f-a617fd7aef02",
      "diskUuid" -> "b3a8022e-e856-45fb-a325-62887951105e",
      "mac" -> "06:74:0a:00:01:3e",
      "range" -> "1d",
      "aggregation" -> "1h",
      "shift" -> "1d"
    )

    brokenParams = Map(
      "uuid" -> "c60e3462-a60c-4f8d-a617fd7aef02",
      "diskUuid" -> "b3a8022e",
      "mac" -> "06:74:0a:00:01",
      "range" -> "1dh",
      "aggregation" -> "1hs",
      "shift" -> "1dh"
    )
  }

  @Test
  def testValidator() = {
    val validator: Validator = new MacValidator
    val (errors, isValid) = validator.validate(params)

    Assert.assertEquals(isValid, true)
    Assert.assertEquals(errors, List())

    val (errorsB, isValidB) = validator.validate(brokenParams)

    Assert.assertEquals(isValidB, false)
    Assert.assertEquals(errorsB, List(validator.message))
  }

  @Test
  def testValidationDecorator() = {
    val validator = new RangeValidator
    val vDecorator = new TimeValidator(validator)

    val (errors, isValid) = vDecorator.validate(params)

    Assert.assertEquals(isValid, true)
    Assert.assertEquals(errors, List())

    val (errorsB, isValidB) = vDecorator.validate(brokenParams)

    Assert.assertEquals(isValidB, false)
    Assert.assertEquals(errorsB, List(vDecorator.message))
  }

  @Test
  def testValidators() = {
    val vmUuidValidator = new UuidValidator(new VmUuidValidator)
    val diskValidator = new UuidValidator(new DiskValidator)
    val macValidator = new MacValidator
    val rangeValidator = new TimeValidator(new RangeValidator)
    val aggregationValidator = new TimeValidator(new AggregationValidator)
    val shiftValidator = new ShiftValidator

    val validators = new Validators(List(
      vmUuidValidator, diskValidator, macValidator,
      rangeValidator, aggregationValidator, shiftValidator
    ))

    val (errors, isValid) = validators.validate(params)

    Assert.assertEquals(isValid, true)
    Assert.assertEquals(errors, List())

    val (errorsB, isValidB) = validators.validate(brokenParams)

    Assert.assertEquals(isValidB, false)
    Assert.assertEquals(errorsB, List(
      vmUuidValidator.message, diskValidator.message, macValidator.message,
      rangeValidator.message, aggregationValidator.message))

  }
}
