package com.bwsw.pulse


import javax.servlet.ServletContext

import com.bwsw.pulse.controllers.PulseController
import com.bwsw.pulse.influx.InfluxUtil
import org.scalatra.LifeCycle

import scala.util.Properties

/**
  *
  */
class PulseBootstrap extends LifeCycle {

  val host = Properties.envOrElse("host", "localhost")
  val port = Properties.envOrElse("port", "8080")
  val username = Properties.envOrElse("username", "")
  val password = Properties.envOrElse("password", "")
  val dbName = Properties.envOrElse("database", "")

  InfluxUtil.createConnection(host, port, username, password, dbName)

  override def init(context: ServletContext) {
    context.mount(new PulseController, "/*")
  }
}
