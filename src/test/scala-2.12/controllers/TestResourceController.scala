package controllers

import com.bwsw.pulse.controllers.PulseController
import com.bwsw.pulse.models.Cpu
import org.scalatra.test.specs2._



class TestResourceController extends MutableScalatraSpec  {

  implicit val swagger = new Cpu

  addServlet(classOf[PulseController], "/*")

  "GET cputime" should {
    "return status 200" in {
      get("/cputime/550e8400-e29b-41d4-a716-446655440000/1d/1h/1w") {
        status must_== 200
      }
    }

    "return status 400" in {
      get("/cputime/12390/1d/1h/1w") {
        status must_== 400
      }
      get("/cputime/550e8400-e29b-41d4-a716-446655440000/1hd/2/hour") {
        status must_== 400
      }
    }

    "return status 404" in {
      get("/cputime") {
        status must_== 404
      }
      get("/cputime/550e8400-e29b-41d4-a716-446655440000") {
        status must_== 404
      }
    }
  }


  "GET ram" should {
    "return status 200" in {
      get("/ram/550e8400-e29b-41d4-a716-446655440000/1d/1h/1w") {
        status must_== 200
      }
    }

    "return status 400" in {
      get("/ram/446655440000/1d/1h/1w") {
        status must_== 400
      }
      get("/ram/550e8400-e29b-41d4-a716-446655440000/1d/1hd/1w") {
        status must_== 400
      }
    }

    "return status 404" in {
      get("/ram") {
        status must_== 404
      }
      get("/ram/550e8400-e29b-41d4-a716-446655440000") {
        status must_== 404
      }
    }
  }


  "GET disk" should {
    "return status 200" in {
      get("/disk/550e8400-e29b-41d4-a716-446655440000/70dc25e9-82c6-4a8c-8d7d-3e304cced576/1h/15m/0s") {
        status must_== 200
      }
    }

    "return status 400" in {
      get("/disk/550e8400-e29b-41d4-a716-446655440000/70dc25e9/1h/15m/0s") {
        status must_== 400
      }
      get("/disk/446655440000/70dc25e9-82c6-4a8c-8d7d-3e304cced576/1h/15m/0s") {
        status must_== 400
      }
      get("/disk/550e8400-e29b-41d4-a716-446655440000/70dc25e9-82c6-4a8c-8d7d-3e304cced576/1h/15m/0sd") {
        status must_== 400
      }
    }

    "return status 404" in {
      get("/disk") {
        status must_== 404
      }
      get("/disk/550e8400-e29b-41d4-a716-446655440000/1h/15m/0sd") {
        status must_== 404
      }
    }
  }


  "GET network" should {
    "return status 200" in {
      get("/network-interface/550e8400-e29b-41d4-a716-446655440000/08:ED:B9:49:B2:E5/1h/15m/0s") {
        status must_==200
      }
    }

    "return status 400" in {
      get("/network-interface/550e8400-e29b-41d4-a716-446655440000/08:ED:B9/1h/15m/0s") {
        status must_== 400
      }
      get("/network-interface/550e8400/08:ED:B9:49:B2:E5/1h/15m/0s") {
        status must_== 400
      }
      get("/network-interface/550e8400-e29b-41d4-a716-446655440000/08:ED:B9:49:B2:E5/1h/15m/0s2") {
        status must_== 400
      }
    }

    "return status 404" in {
      get("/network-interface") {
        status must_== 404
      }
      get("/network-interface/550e8400-e29b-41d4-a716-446655440000/1h/15m/0s") {
        status must_== 404
      }
    }
  }
}
