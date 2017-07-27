package com.bwsw.cloudstack.pulse

import javax.servlet.ServletContext

import com.bwsw.cloudstack.pulse.config.PulseConfig
import com.bwsw.cloudstack.pulse.controllers.PulseController
import com.bwsw.cloudstack.pulse.influx.InfluxService
import org.scalatra.LifeCycle



class PulseBootstrap extends LifeCycle {

  InfluxService.connect(
    PulseConfig.influx.url,
    PulseConfig.influx.username,
    PulseConfig.influx.password,
    PulseConfig.influx.database)

  override def init(context: ServletContext) {
    context.mount(new PulseController, "/*")
  }
}
