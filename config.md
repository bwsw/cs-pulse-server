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
  aggregations_allowed = [
    {
      range = "15m"
      aggregation = ["1m", "5m"]
    }
    {
      range = "30m"
      aggregation = ["1m", "5m", "15m"]
    }
    {
      range = "1h"
      aggregation = ["1m", "5m", "15m"]
    }
    {
      range = "2h"
      aggregation = ["5m", "15m", "30m"]
    }
    {
      range = "4h"
      aggregation = ["5m", "15m", "30m", "1h"]
    }
    {
      range = "12h"
      aggregation = ["15m", "30m", "1h"]
    }
    {
      range = "1d"
      aggregation = ["30m", "1h"]
    }
    {
      range = "1w"
      aggregation = ["1h", "4h"]
    }
    {
      range = "30d"
      aggregation = ["4h", "1d"]
    }
   
  ]
  shift = ["m", "h", "d"]
}
```
