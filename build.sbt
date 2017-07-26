name := "cs-pulse-server"

version := "1.0.2-SNAPSHOT"

scalaVersion := "2.12.2"

pomExtra :=
  <scm>
    <url>git@github.com:bwsw/cs-pulse-server.git</url>
    <connection>scm:git@github.com:bwsw/cs-pulse-server.git</connection>
  </scm>
    <developers>
      <developer>
        <id>bitworks</id>
        <name>Bitworks Software, Ltd.</name>
        <url>http://bitworks.software/</url>
      </developer>
    </developers>


fork in run := true
fork in Test := true
licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
homepage := Some(url("https://github.com/bwsw/cs-pulse-server"))
pomIncludeRepository := { _ => false }
scalacOptions += "-feature"
scalacOptions += "-deprecation"
parallelExecution in Test := false
organization := "com.bwsw"
publishMavenStyle := true
pomIncludeRepository := { _ => false }

isSnapshot := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

libraryDependencies += "org.scalatra" % "scalatra_2.12" % "2.5.0"
libraryDependencies += "org.influxdb" % "influxdb-java" % "2.5"
libraryDependencies += "javax.servlet" % "servlet-api" % "2.5"
libraryDependencies += "org.scalatra" %% "scalatra-json" % "2.5.0"
libraryDependencies += "org.json4s"   %% "json4s-jackson" % "3.5.2"
libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "9.4.5.v20170502"
libraryDependencies += "org.eclipse.jetty" % "jetty-servlet" % "9.4.5.v20170502"
libraryDependencies += "org.eclipse.jetty" % "jetty-webapp" % "9.4.5.v20170502"
libraryDependencies += "org.scalatra" %% "scalatra-specs2" % "2.5.0" % "test"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7" % "runtime"
libraryDependencies += "junit" % "junit" % "4.4"
libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19"
libraryDependencies += "com.typesafe" % "config" % "1.3.1"

libraryDependencies += "com.novocode" % "junit-interface" % "0.8" % "test->default"

enablePlugins(WarPlugin)