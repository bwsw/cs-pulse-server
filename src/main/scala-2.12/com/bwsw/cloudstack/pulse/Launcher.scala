package com.bwsw.cloudstack.pulse

/**
  * Created by diryavkin_dn on 19.05.17.
  */
// remember this package in the sbt project definition

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

object Launcher {
  def main(args: Array[String]) {
    val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 3000

    val server = new Server(port)
    val context = new WebAppContext()
    context.setContextPath("/pulse")
    context.setResourceBase("src/main/webapp")
    context.addEventListener(new ScalatraListener)

    server.setHandler(context)

    server.start()
    server.join()
  }
}