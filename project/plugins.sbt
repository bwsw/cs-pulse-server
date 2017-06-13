addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.4")
addSbtPlugin("org.scalatra.sbt" % "scalatra-sbt" % "0.5.1")
addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "3.0.2")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")