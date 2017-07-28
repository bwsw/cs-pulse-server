package com.bwsw.cloudstack.pulse.controllers

import com.bwsw.cloudstack.pulse.config.PulseConfig
import com.bwsw.cloudstack.pulse.models._
import com.bwsw.cloudstack.pulse.validators.complex.{DiskRequestValidator, NetworkInterfaceRequestValidator, CPURAMRequestValidator}
import com.bwsw.cloudstack.pulse.validators.{PrimitiveValidator, Validator}
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

  def createResourceView(viewFabric: MetricsViewBuilder, params: scalatra.Params): ActionResult = {
    val queryResult = viewFabric.getTable.getResult(params)
    val viewResult = viewFabric.prepareView(queryResult, params)
    if (viewResult._1) Ok(viewResult._2)
    else InternalServerError(viewResult._2)
  }

  def handle(view: MetricsViewBuilder, params: scalatra.Params, validator: Validator) = {
    val result = validator.validate(params)

    result match {
      case Left(v) => createResourceView(view, params)
      case Right(v) =>
        logger.error(v)
        BadRequest(ErrorView(params, List(v)))
    }
  }

  private val cpuView = new CpuViewBuilder(new CPUInfluxModel)
  private val ramView = new RamViewBuilder(new RAMInfluxModel)
  private val diskView = new DiskViewBuilder(new DiskInfluxModel)
  private val networkView = new NetworkViewBuilder(new NetworkInfluxModel)

  before() {
    contentType = formats("json")
  }

  get("/cputime/:uuid/:range/:aggregation/:shift") {
    logger.debug(s"Cpu Time Request Parameters: $params")

    handle(cpuView, params, new CPURAMRequestValidator)
  }

  get("/ram/:uuid/:range/:aggregation/:shift") {
    logger.debug(s"Ram Request Parameters: $params")

    handle(ramView, params, new CPURAMRequestValidator)
  }

  get("/network-interface/:uuid/:mac/:range/:aggregation/:shift") {
    logger.debug(s"Network Request Parameters: $params")

    handle(networkView, params, new NetworkInterfaceRequestValidator)
  }

  get("/disk/:uuid/:diskUuid/:range/:aggregation/:shift") {
    logger.debug(s"Disk Request Parameters: $params")

    handle(diskView, params, new DiskRequestValidator)
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
