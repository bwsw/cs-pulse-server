package com.bwsw.cloudstack.pulse.controllers

import com.bwsw.cloudstack.pulse.config.PulseConfig
import com.bwsw.cloudstack.pulse.models._
import com.bwsw.cloudstack.pulse.validators._
import com.bwsw.cloudstack.pulse.views._
import org.json4s.DefaultFormats
import org.scalatra
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import org.slf4j.LoggerFactory


class PulseController extends ScalatraServlet with JacksonJsonSupport {

  protected implicit lazy val jsonFormats = DefaultFormats
  protected val applicationDescription = "CloudStack Extensions / Pulse Plugin Server"

  private val logger =  LoggerFactory.getLogger(getClass)

  def createResourceView(viewFabric: MetricsViewFabric, params: scalatra.Params): ActionResult = {
    val queryResult = viewFabric.getTable.getResult(params)
    val viewResult = viewFabric.prepareView(queryResult, params)
    if (viewResult._1) Ok(viewResult._2)
    else InternalServerError(viewResult._2)
  }

  def handle(view: MetricsViewFabric, params: scalatra.Params, validator: Validator) = {
    val (errors, isValid) = validator.validate(params)

    isValid match {
      case true => createResourceView(view, params)
      case false =>
        logger.debug(errors.toString())
        BadRequest(ErrorView(params, errors))
    }
  }

  private val vmValidator = new UuidValidator(new VmUuidValidator)
  private val diskUuidValidator = new UuidValidator(new DiskValidator)
  private val rangeValidator = new TimeFormatValidator(new RangeValidator)
  private val aggregationValidator = new AggregationRangeValidator(new TimeFormatValidator(new AggregationValidator))
  private val macValidator = new MacValidator
  private val shiftValidator = new TimeFormatValidator(new ShiftValidator)

  private val cpuValidator = new Validators(List(vmValidator, rangeValidator,
    aggregationValidator, shiftValidator))
  private val ramValidator = new Validators(List(vmValidator, rangeValidator,
    aggregationValidator, shiftValidator))
  private val diskValidator = new Validators(List(vmValidator, diskUuidValidator, rangeValidator,
    aggregationValidator, shiftValidator))
  private val networkValidator = new Validators(List(vmValidator, macValidator, rangeValidator,
    aggregationValidator, shiftValidator))


  private val cpuView = new CpuViewFabric(new CPUInfluxModel)
  private val ramView = new RamViewFabric(new RAMInfluxModel)
  private val diskView = new DiskViewFabric(new DiskInfluxModel)
  private val networkView = new NetworkViewFabric(new NetworkInfluxModel)

  before() {
    contentType = formats("json")
  }

  get("/cputime/:uuid/:range/:aggregation/:shift") {
    logger.debug(s"Cpu Time Request Parameters: $params")

    handle(cpuView, params, cpuValidator)
  }

  get("/ram/:uuid/:range/:aggregation/:shift") {
    logger.debug(s"Ram Request Parameters: $params")

    handle(ramView, params, ramValidator)
  }

  get("/network-interface/:uuid/:mac/:range/:aggregation/:shift") {
    logger.debug(s"Network Request Parameters: $params")

    handle(networkView, params, networkValidator)
  }

  get("/disk/:uuid/:diskUuid/:range/:aggregation/:shift") {
    logger.debug(s"Disk Request Parameters: $params")

    handle(diskView, params, diskValidator)
  }

  get("/permitted-intervals") {
    logger.debug(s"Permitted Intervals Request Parameters")

    PermittedIntervals(PulseConfig().shifts, PulseConfig().scales)
  }

  notFound {
    logger.debug(s"Requested Page Is Not Found")

    NotFound(ErrorView(Map(), List("Page Is Not Found")))
  }
}
