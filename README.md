# cs-pulse-server
RESTful server for bwsw/cs-pulse-sensor datafeed processing

### Server specification

##### Request types

1. CPU time, query param /cputime/uuid/range/aggregation/shift
* uuid - host uuid
* range - Data output period - 15m, 30m, 1h, 2h, 4h, 12h, 1d, 1w, 1m
* shift - 0+ which means N-fold shift in current  range
* aggregation - 1m, 5m, 15m, 1h (aggregation must be less then range), example: range=15m, aggregation=5m, in result we have 3 values for needed data.
 Can have only one parameter which is gotten from a sensor
 
2. RAM utilization /ram/uuid/range/aggregation/shift
* Can have only one parameter which is gotten from a sensor
* Data output should have bits dimension

3. Network utilization /network-interface/uuid/mac/range/aggregation/shift
* have addition value - mac - mac card address
* can have many parameters which are gotten from a sensor

4. Disk utilization /disk/uuid/disk-uuid/range/aggregation/shift
* have addition value disk-uuid
* can have many parameters which are gotten from a sensor

##### Features:

- [x] &nbsp; Implement with trivial HTTP REST framework like Scalatra or Play,
- [ ] &nbsp; build and deploy as docker container,
- [ ] &nbsp; attach to dockerhub
- [x] &nbsp; no external database required (except influxdb)
- [ ] &nbsp; implement per-client query limit/sec parameter configurable with ENVARS with Nginx (#20 queries per second) 
- [ ] &nbsp; implement behind nginx
- [x] &nbsp; valid RESTful GET methods according to above specification
- [x] &nbsp; JSON for responses
- [ ] &nbsp; Configurable nginx cache time (in seconds) for repeatable requests, default 10 seconds
- [x] &nbsp; Set what kind of aggregation can there be for each range.





### Data json format:
```
results: [
  {
    <field>: <value>,
    ...
  }, 
  {...}
]
```

### Metadata json format:
```
{
  measurement: <measurement>,
  uuid: <vm uuid>,
  range: <time>,
  aggregation: <time>,
  shift: <time>,
  <
  mac: <mac address>,
  disk_uuid: <disk uuid>,
  >
  results: [...]
}
```
### Design:
https://github.com/bwsw/cs-pulse-server/wiki/Design

### Request/Response examples:

##### Cpu request:
``` 
http://hostname/cputime/550e8400-e29b-41d4-a716-446655440000/1d/1h/1w
```
##### Cpu response:
```
{
    measurement: cputime,
    uuid: 550e8400-e29b-41d4-a716-446655440000,
    range: 1d,
    aggregation: 1h,
    shift: 1w,
    results: 
    [
        {
            cpu: 50
        },
        {
            cpu: 70
        },
        {
            cpu: 40
        }
    ]
}
```

##### Ram request:
```
http://hostname/ram/550e8400-e29b-41d4-a716-446655440000/15m/1m/1d
```
##### Ram response:
```
{
    measurement: ram,
    uuid: 550e8400-e29b-41d4-a716-446655440000,
    range: 15m,
    aggregation: 1m,
    shift: 1d,
    result: 
    [
        {
            rss: 3.5
        },
        {
            rss: 3.3
        },
        {
            rss: 3.6
        }
    ]
}
```

##### Disk request:
```
http://hostname/disk/550e8400-e29b-41d4-a716-446655440000/70dc25e9-82c6-4a8c-8d7d-3e304cced576/1h/15m/0s
```
##### Disk response:
```
{
    measurement: disk,
    uuid: 550e8400-e29b-41d4-a716-446655440000,
    diskUuid: 70dc25e9-82c6-4a8c-8d7d-3e304cced576,
    range: 1h,
    aggregation: 15m,
    shift: 0s,
    result: 
    [
        {
            ioErrors: -1,
            readBytes: 4096,
            writeBytes: 2108076032,
            readIOPS: 1,
            writeIOPS: 489850
        },
        {
            ioErrors: -1,
            readBytes: 144589824,
            writeBytes: 1702346752,
            readIOPS: 12802,
            writeIOPS: 169427
        }
    ]
}
```
##### Network request:
```
http://hostname/network-interface/550e8400-e29b-41d4-a716-446655440000/08:ED:B9:49:B2:E5/1h/15m/0s
```
##### Network response: 
```
{
    measurement: network-interface,
    uuid: 550e8400-e29b-41d4-a716-446655440000,
    mac: 08:ED:B9:49:B2:E5,
    range: 1h,
    aggregation: 15m,
    shift: 0s,
    result: 
    [
        {
            readBytes: 354920233,
            writeBytes: 233636521,
            readErrors: 0,
            writeErrors: 0,
            readDrops: 0,
            writeDrops: 0,
            readPackets: 1644729,
            writePackets: 797443
        },
        {
            readBytes: 17842072,
            writeBytes: 15747525,
            readErrors: 0,
            writeErrors: 0,
            readDrops: 0,
            writeDrops: 0,
            readPackets: 108813,
            writePackets: 58346
        }
    ]
}
```


```
{
shift: ["m", "h", "d"],
aggregations_allowed: [
    {
        range: "15m",
        aggregation: ["1m", "5m"]
    },
    {
        range: "30m",
        aggregation: ["1m", "5m", "15m"]
    }
  ]
}
```


##### Response with null value:
```
{
    measurement: cputime,
    uuid: 550e8400-e29b-41d4-a716-446655440000,
    range: 1d,
    aggregation: 1h,
    shift: 1w,
    results: 
    [
        {
            cpu: "50"
        },
        {
            cpu: "70"
        },
        {
            cpu: "40"
        },
        {
            cpu: ""
        },
        {
            cpu: ""
        },
        {
            cpu: "60"
        }
    ]
}
```


##### Error response:
Response status code: 503, 400, 404

Response example:
```
{
    measurement: cputime,
    uuid: 550e8400-e29b-41d4-a716,
    range: 1dh,
    aggregation: 1h,
    shift: 1w,
    errors:
    [
        "Argument range must be in influx time format, with suffix",
        "Argument uuid must be in UUID format"
    ]
}
```


##### Known error messages:
```
Page not found
Argument <fieldName> not included into allowed interval. See configuration file.
Argument <fieldName> not matches.
Argument <fildname>: <another fieldname> argument must be specified firstly.
Argument <fieldName> must be in influx time format, with suffix.
Argument <fieldName> must be in UUID format.
```

Additional error messages from QueryResult.