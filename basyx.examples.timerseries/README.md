# BaSyx Time Series example

This is an example a query operation implementation in a time series submodel according to the proposed operations from the [IDTA TimeSeries Submodel](https://github.com/admin-shell-io/submodel-templates/tree/main/published/Time%20Series%20Data/1/1) with influxDB as the database.

The example has two implementations of the query operation

-   ReadRecords operation that adheres to the proposition in the standard, which returns a SubmodelElementCollection containing the results
-   ReadRecordsShort operation that returns a property that has the result of the query operation as a json string. This was implemented because returning submodel elements as a result was becoming too large and introducing big overhead by repeating the submodel element structure for each value returned.

The example uses influxDB for time series data retrieval. An example of a time series submodel for an air sensor is provided as aasx. The java app imports this aasx and adds an invokable function to the operations: ReadRecords and ReadRecordsShort. The query is built by reading the MetaData and taking the Name property (airSensors) as the measurement field in the influxDB. The bucket is hardcoded as "airsensors". The InfluxDBConnection class contains hardcoded connection data.

Querying influxDB data needs a container with annotations. The AirSensor class implements the IQueryContainer interface and annotates its fields.

## How to run

-   `Docker compose up` to spin up AAS server, registry, influxDB instance, and a time series submodel example.

Now you can run the ReadRecords and ReadRecordsShort operation and query the data. The following post request can serve as an example:

POST: http://localhost:8002/AirSensor/TimeSeries/submodel/submodelElements/ReadRecords/invoke
POST: http://localhost:8002/AirSensor/TimeSeries/submodel/submodelElements/ReadRecordsShort/invoke
Body:

```json
{
    "requestId": "null",
    "inputArguments": [
        {
            "value": {
                "idShort": "Timespan",
                "modelType": {
                    "name": "Range"
                },
                "valueType": "dateTimeStamp",
                "min": "2023-07-25T07:30:00Z",
                "max": "2023-07-25T07:45:00Z"
            }
        }
    ],
    "timeout": 0
}
```
