package com.bwsw.cloudstack.pulse

import javax.servlet.ServletContext

import com.bwsw.cloudstack.pulse.config.PulseConfig
import com.bwsw.cloudstack.pulse.controllers.PulseController
import com.bwsw.cloudstack.pulse.influx.InfluxUtil
import org.scalatra.LifeCycle



class PulseBootstrap extends LifeCycle {

  InfluxUtil.createConnection(
    PulseConfig.influx.host,
    PulseConfig.influx.port,
    PulseConfig.influx.username,
    PulseConfig.influx.password,
    PulseConfig.influx.database)

  override def init(context: ServletContext) {
    context.mount(new PulseController, "/*")
  }
}
