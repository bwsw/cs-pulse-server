package models

import org.junit._
import com.bwsw.pulse.models._

class TestPrepareQuery {

  var cpuTestQuery: String = _
  var cpuTestParams: Map[String, String] = _

  var ramTestQuery: String = _
  var ramTestParams: Map[String, String] = _

  var diskTestQuery: String = _
  var diskTestParams: Map[String, String] = _

  var networkTestQuery: String = _
  var networkTestParams: Map[String, String] =_

  @Before
  def initialize() = {
    cpuTestParams = Map(
      "uuid" -> "87b753e0-da9d-474c-a7fa-9c942ed2a0ea",
      "range" -> "1d",
      "aggregation" -> "1h",
      "shift" -> "1d"
    )
    cpuTestQuery = "SELECT DERIVATIVE(MEAN(\"cpuTime\"),1h) / LAST(\"cpus\") / 60 * 100 AS \"cpu\" " +
      "FROM \"cpuTime\" WHERE \"vmUuid\" = '87b753e0-da9d-474c-a7fa-9c942ed2a0ea' " +
      "AND time > now() - 1d - 1d AND time < now() - 1d GROUP BY time(1h)"

    ramTestParams = Map(
      "uuid" -> "87b753e0-da9d-474c-a7fa-9c942ed2a0ea",
      "range" -> "1d",
      "aggregation" -> "1h",
      "shift" -> "1d"
    )
    ramTestQuery = "SELECT MEAN(\"rss\") AS \"rss\" FROM \"rss\" " +
      "WHERE \"vmUuid\" = '87b753e0-da9d-474c-a7fa-9c942ed2a0ea' " +
      "AND time > now() - 1d - 1d AND time < now() - 1d GROUP BY time(1h)"

    diskTestParams = Map(
      "uuid" -> "87b753e0-da9d-474c-a7fa-9c942ed2a0ea",
      "range" -> "1d",
      "aggregation" -> "1h",
      "shift" -> "1d",
      "diskUuid" -> "938c8420-3e42-4b34-96a9-8479ef9d0f88"
    )
    diskTestQuery = "SELECT MEAN(\"ioErrors\") AS \"ioErrors\", " +
      "MEAN(\"readBytes\") AS \"readBytes\", " +
      "MEAN(\"writeBytes\") AS \"writeBytes\", " +
      "MEAN(\"readIOPS\") AS \"readIOPS\", " +
      "MEAN(\"writeIOPS\") AS \"writeIOPS\" " +
      "FROM \"disk\" WHERE \"vmUuid\" = '87b753e0-da9d-474c-a7fa-9c942ed2a0ea' " +
      "AND \"image\" = '938c8420-3e42-4b34-96a9-8479ef9d0f88' " +
      "AND time > now() - 1d - 1d AND time < now() - 1d GROUP BY time(1h)"

    networkTestParams = Map(
      "uuid" -> "87b753e0-da9d-474c-a7fa-9c942ed2a0ea",
      "range" -> "1d",
      "aggregation" -> "1h",
      "shift" -> "1d",
      "mac" -> "06:42:be:00:01:2d"
    )
    networkTestQuery = "SELECT MEAN(\"ioErrors\") AS \"ioErrors\", " +
      "MEAN(\"readBytes\") AS \"readBytes\", " +
      "MEAN(\"writeBytes\") AS \"writeBytes\", " +
      "MEAN(\"readIOPS\") AS \"readIOPS\", " +
      "MEAN(\"writeIOPS\") AS \"writeIOPS\" FROM \"networkInterface\" " +
      "WHERE \"vmUuid\" = '87b753e0-da9d-474c-a7fa-9c942ed2a0ea' " +
      "AND \"mac\" = '06:42:be:00:01:2d' " +
      "AND time > now() - 1d - 1d AND time < now() - 1d GROUP BY time(1h)"
  }

  @Test
  def testCpuPrepareQuery() = {
    val cpu: Resource = new Cpu
    Assert.assertEquals(cpu.prepareQuery(cpuTestParams), cpuTestQuery)
  }

  @Test
  def testRamPrepareQuery() = {
    val ram: Resource = new Ram
    Assert.assertEquals(ram.prepareQuery(ramTestParams), ramTestQuery)
  }

  @Test
  def testDiskPrepareQuery() = {
    val disk: Resource = new Disk
    Assert.assertEquals(disk.prepareQuery(diskTestParams), diskTestQuery)
  }

  @Test
  def testNetworkPrepareQuery() = {
    val network: Resource = new Network
    Assert.assertEquals(network.prepareQuery(networkTestParams), networkTestQuery)
  }
}
