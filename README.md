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
- [ ] &nbsp; no external database required (except influxdb)
- [ ] &nbsp; implement per-client query limit/sec parameter configurable with ENVARS with Nginx (#20 queries per second) 
- [ ] &nbsp; implement behind nginx
- [x] &nbsp; valid RESTful GET methods according to above specification
- [x] &nbsp; JSON for responses
- [ ] &nbsp; Configurable nginx cache time (in seconds) for repeatable requests, default 10 seconds
- [ ] &nbsp; Set what kind of aggregation can there be for each range.





### Data json format:
```
results: [
  {
    time: <timestamp>,
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
### Class diagram:
![alt text](https://github.com/bwsw/cs-pulse-server/blob/master/PulseDiagram2.png?raw=true)

### Component diagram:
![alt text](https://github.com/bwsw/cs-pulse-server/blob/master/pulse_component.png?raw=true)

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
            time: 1495095253538261861,
            cpu: 50
        },
        {
            time: 1495095733576515764,
            cpu: 70
        },
        {
            time: 1495096105019488941,
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
            time: 1495096105019488941,
            rss: 3.5
        },
        {
            time: 1495096170818385758,
            rss: 3.3
        },
        {
            time: 1495096269086018493,
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
            time: 1495096861491604231,
            ioErrors: -1,
            readBytes: 4096,
            writeBytes: 2108076032,
            readIOPS: 1,
            writeIOPS: 489850
        },
        {
            time: 1495096861538785936,
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
            time: 1495097630092157592,
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
            time: 1495097630092157592,
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
