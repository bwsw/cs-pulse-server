```
pulse_config {
  influx {
    host =  "host"
    host = ${?INFLUX_HOST}
    
    port = "port"
    port = ${?INFLUX_PORT}
    
    username = "username"
    username = ${?INFLUX_USERNAME}
    
    password = "password"
    password = ${?INFLUX_PASSWORD}
    
    database = "database"
    database = ${?INFLUX_DATABASE}
  }
  time = [
    {
      range = "15m"
      spec {
        aggregation = ["1m", "5m"]
      }
    }
    {
      range = "30m"
      spec {
        aggregation = ["1m", "5m", "15m"]
      }
    }
    {
      range = "1h"
      spec {
        aggregation = ["1m", "5m", "15m"]
      }
    }
    {
      range = "2h"
      spec {
        aggregation = ["5m", "15m", "30m"]
      }
    }
    {
      range = "4h"
      spec {
        aggregation = ["5m", "15m", "30m", "1h"]
      }
    }
    {
      range = "12h"
      spec {
        aggregation = ["15m", "30m", "1h"]
      }
    }
    {
      range = "1d"
      spec {
        aggregation = ["30m", "1h"]
      }
    }
    {
      range = "1w"
      spec {
        aggregation = ["1h", "4h"]
      }
    }
    {
    range = "30d"
    spec {
      aggregation = ["4h", "1d"]
    }
    }
  ]
  shift = ["m", "h", "d"]
}
```
