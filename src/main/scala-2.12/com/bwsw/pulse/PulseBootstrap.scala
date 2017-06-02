package com.bwsw.pulse


import javax.servlet.ServletContext

import com.bwsw.pulse.config.PulseConfig
import com.bwsw.pulse.controllers.PulseController
import com.bwsw.pulse.influx.InfluxUtil
import org.scalatra.LifeCycle



class PulseBootstrap extends LifeCycle {

  InfluxUtil.createConnection(
    PulseConfig.influx_connection.host,
    PulseConfig.influx_connection.port,
    PulseConfig.influx_connection.username,
    PulseConfig.influx_connection.password,
    PulseConfig.influx_connection.database)

  override def init(context: ServletContext) {
    context.mount(new PulseController, "/*")
  }
}
