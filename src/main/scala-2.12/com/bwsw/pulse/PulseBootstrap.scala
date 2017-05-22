package com.bwsw.pulse


import javax.servlet.ServletContext

import com.bwsw.pulse.controllers.PulseController
import org.scalatra.LifeCycle

class PulseBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new PulseController, "/*")
  }
}
