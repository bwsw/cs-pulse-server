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
    cpuTestQuery = "SELECT NON_NEGATIVE_DERIVATIVE(MEAN(\"cpuTime\"), 1h) / LAST(\"cpus\") / 3600 * 100 AS \"cpuTime\" " +
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
    diskTestQuery = "SELECT NON_NEGATIVE_DERIVATIVE(MEAN(\"ioErrors\"), 1h) / 3600 AS \"ioErrors\", NON_NEGATIVE_DERIVATIVE(MEAN(\"readBytes\"), 1h) / 3600 AS \"readBytes\", NON_NEGATIVE_DERIVATIVE(MEAN(\"writeBytes\"), 1h) / 3600 AS \"writeBytes\", NON_NEGATIVE_DERIVATIVE(MEAN(\"readIOPS\"), 1h) / 3600 AS \"readIOPS\", NON_NEGATIVE_DERIVATIVE(MEAN(\"writeIOPS\"), 1h) / 3600 AS \"writeIOPS\" FROM \"disk\" WHERE \"vmUuid\" = '87b753e0-da9d-474c-a7fa-9c942ed2a0ea' AND \"image\" = '938c8420-3e42-4b34-96a9-8479ef9d0f88' AND time > now() - 1d - 1d AND time < now() - 1d GROUP BY time(1h)"

    networkTestParams = Map(
      "uuid" -> "87b753e0-da9d-474c-a7fa-9c942ed2a0ea",
      "range" -> "1d",
      "aggregation" -> "1h",
      "shift" -> "1d",
      "mac" -> "06:42:be:00:01:2d"
    )
    networkTestQuery = "SELECT NON_NEGATIVE_DERIVATIVE(MEAN(\"readBytes\"), 1h) / 3600 AS \"readBytes\", NON_NEGATIVE_DERIVATIVE(MEAN(\"writeBytes\"), 1h) / 3600 AS \"writeBytes\", NON_NEGATIVE_DERIVATIVE(MEAN(\"readErrors\"), 1h) / 3600 AS \"readErrors\", NON_NEGATIVE_DERIVATIVE(MEAN(\"writeErrors\"), 1h) / 3600 AS \"writeErrors\", NON_NEGATIVE_DERIVATIVE(MEAN(\"readDrops\"), 1h) / 3600 AS \"readDrops\", NON_NEGATIVE_DERIVATIVE(MEAN(\"writeDrops\"), 1h) / 3600 AS \"writeDrops\", NON_NEGATIVE_DERIVATIVE(MEAN(\"readPackets\"), 1h) / 3600 AS \"readPackets\", NON_NEGATIVE_DERIVATIVE(MEAN(\"writePackets\"), 1h) / 3600 AS \"writePackets\" FROM \"networkInterface\" WHERE \"vmUuid\" = '87b753e0-da9d-474c-a7fa-9c942ed2a0ea' AND \"mac\" = '06:42:be:00:01:2d' AND time > now() - 1d - 1d AND time < now() - 1d GROUP BY time(1h)"
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
