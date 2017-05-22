package com.bwsw.pulse.controllers

import com.bwsw.pulse.models._
import com.paulgoldbaum.influxdbclient.QueryResult
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport
import com.bwsw.pulse.views._
import org.scalatra


class PulseController extends ScalatraServlet with JacksonJsonSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  protected val applicationDescription = "Test App"

  val cpu: Resource = new Cpu
  val cpuView: View = new CpuView

  val ram: Resource = new Ram
  val ramView: View = new RamView

  val disk: Resource = new Disk
  val diskView: View = new DiskView

  val network: Resource = new Network
  val networkView: View = new NetworkView

  before() {
    contentType = formats("json")
  }

  get("/cputime/:uuid/:range/:aggregation/:shift") {
    createResourceView(cpu, cpuView, params)
  }

  get("/ram/:uuid/:range/:aggregation/:shift") {
    createResourceView(ram, ramView, params)
  }

  get("/network/:uuid/:mac/:range/:aggregation/:shift") {
    createResourceView(network, networkView, params)
  }

  get("/disk/:uuid/:diskUuid/:range/:aggregation/:shift") {
    createResourceView(disk, diskView, params)
  }

  def createResourceView(concreteResource: Resource, concreteView: View, params: scalatra.Params): CaseClassView = {
    val sourceData: QueryResult = concreteResource.getResult(params)
    val view = concreteView.prepareView(sourceData)
    view
  }
}


