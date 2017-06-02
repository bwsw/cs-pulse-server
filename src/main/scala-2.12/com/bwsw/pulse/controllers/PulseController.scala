package com.bwsw.pulse.controllers

import com.bwsw.pulse.config.PulseConfig
import com.bwsw.pulse.models._
import com.bwsw.pulse.validators._
import org.influxdb.dto.QueryResult
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import com.bwsw.pulse.views._
import org.scalatra
import org.slf4j.{Logger, LoggerFactory}


class PulseController extends ScalatraServlet with JacksonJsonSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  protected val applicationDescription = "Pulse Server"

  val logger: Logger =  LoggerFactory.getLogger(getClass)


  def createResourceView(concreteResource: Resource, concreteView: ViewFabric, params: scalatra.Params): View = {
    val sourceData: QueryResult = concreteResource.getResult(params)
    val view = concreteView.prepareView(sourceData, params)
    view
  }

  def mainHandler(concreteResource: Resource, concreteView: ViewFabric, params: scalatra.Params, validator: Validator) = {
    val (errors, isValid) = validator.validate(params)
    isValid match {
      case true => createResourceView(concreteResource, concreteView, params)
      case false => BadRequest(errors)
    }
  }


  val vmValidator = new UuidValidator(new VmUuidValidator)
  val diskUuidValidator = new UuidValidator(new DiskValidator)
  val rangeValidator = new TimeFormatValidator(new RangeValidator)
  val aggregationValidator = new TimeFormatValidator(new AggregationValidator)
  val macValidator = new MacValidator
  val shiftValidator = new TimeFormatValidator(new ShiftValidator)

  val cpuValidator = new Validators(List(vmValidator, rangeValidator,
    aggregationValidator, shiftValidator))
  val ramValidator = new Validators(List(vmValidator, rangeValidator,
    aggregationValidator, shiftValidator))
  val diskValidator = new Validators(List(vmValidator, diskUuidValidator, rangeValidator,
    aggregationValidator, shiftValidator))
  val networkValidator = new Validators(List(vmValidator, macValidator, rangeValidator,
    aggregationValidator, shiftValidator))


  val cpu: Resource = new Cpu
  val cpuView: ViewFabric = new CpuViewFabric

  val ram: Resource = new Ram
  val ramView: ViewFabric = new RamViewFabric

  val disk: Resource = new Disk
  val diskView: ViewFabric = new DiskViewFabric

  val network: Resource = new Network
  val networkView: ViewFabric = new NetworkViewFabric



  before() {
    contentType = formats("json")
  }

  get("/cputime/:uuid/:range/:aggregation/:shift") {
    logger.debug(s"Cpu time: $params")

    mainHandler(cpu, cpuView, params, cpuValidator)
  }

  get("/ram/:uuid/:range/:aggregation/:shift") {
    logger.debug(s"Ram: $params")

    mainHandler(ram, ramView, params, ramValidator)
  }

  get("/network-interface/:uuid/:mac/:range/:aggregation/:shift") {
    logger.debug(s"Network: $params")

    mainHandler(network, networkView, params, networkValidator)
  }

  get("/disk/:uuid/:diskUuid/:range/:aggregation/:shift") {
    logger.debug(s"Disk: $params")

    mainHandler(disk, diskView, params, diskValidator)
  }

  get("/permitted-intervals") {
    logger.debug(s"Permitted intervals")

    PermittedIntervals(PulseConfig.shift_config, PulseConfig.range_config)
  }
}
