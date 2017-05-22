name := "pulse-plugin"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies += "org.scalatra" % "scalatra_2.12" % "2.5.0"
libraryDependencies += "com.paulgoldbaum" %% "scala-influxdb-client" % "0.5.2"
libraryDependencies += "javax.servlet" % "servlet-api" % "2.5"
libraryDependencies += "org.scalatra" %% "scalatra-json" % "2.5.0"
libraryDependencies += "org.json4s"   %% "json4s-jackson" % "3.5.2"
libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "9.4.5.v20170502"
libraryDependencies += "org.eclipse.jetty" % "jetty-servlet" % "9.4.5.v20170502"
libraryDependencies += "org.eclipse.jetty" % "jetty-webapp" % "9.4.5.v20170502"
