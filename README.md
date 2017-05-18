# cs-pulse-server
RESTful server for bwsw/cs-pulse-sensor datafeed processing

### Server specification¶

##### Request types

1. CPU time, query param /cputime/<uuid>/< range>/<aggregation>/<shift>
<uuid> - host uuid
< range> - Data output period - 15m, 30m, 1h, 2h, 4h, 12h, 1d, 1w, 1m
<shift> - 0+ which means N-fold shift in current  range
<aggregation> - 1m, 5m, 15m, 1h (aggregation must be less then range), example: range=15m, aggregation=5m, in result we have 3 values for needed data.
 Can have only one parameter which is gotten from a sensor
 
2. RAM utilization /ram/<uuid>/< range>/<aggregation>/<shift>
Can have only one parameter which is gotten from a sensor
Data output should have bits dimension

3. Network utilization /network-interface/<uuid>/<mac>/< range>/<aggregation>/<shift>
have addition value - mac - mac card address
can have many parameters which are gotten from a sensor

4. Disk utilization /disk/<uuid>/<disk-uuid>/< range>/<aggregation>/<shift>
have addition value disk-uuid
can have many parameters which are gotten from a sensor

##### Features:

* Implement with trivial HTTP REST framework like Scalatra or Play,
* build and deploy as docker container,
* attach to dockerhub
* no external database required (except influxdb)
* implement per-client query limit/sec parameter configurable with ENVARS with Nginx (#20 queries per second) 
* implement behind nginx
* valid RESTful GET methods according to above specification
* JSON for responses
* Configurable nginx cache time (in seconds) for repeatable requests, default 10 seconds
* Set what kind of aggregation can there be for each range.





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
![alt text](https://github.com/bwsw/cs-pulse-server/blob/master/PulseSchema.png?raw=true)

### Component diagram:
![alt text](https://github.com/bwsw/cs-pulse-server/blob/master/pulse_component.png?raw=true)
