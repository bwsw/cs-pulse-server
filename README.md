# cs-pulse-server
RESTful server for bwsw/cs-pulse-sensor datafeed processing

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
### Class diagramm:
