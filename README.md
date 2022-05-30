Endpoint Monitoring
===================
Interview task for [Applifting s.r.o.](https://applifting.cz)

## What is this?

This is an app that serves an API that can be used to monitor HTTP addresses, to
see what they like in the past at regular intervals.

## The API

### Get a list of monitored endpoints

```
GET /endpoints
Parameter: id -- The ID of an existing endpoint
```

Example request:

```
GET /endpoints?id=3
```

Example response:

```json
[
    {
        "id": 19,
        "dateOfCheck": "2022-05-30T12:36:25.827189Z",
        "httpStatusCode": 200,
        "payload": "{\"activity\":\"Start a garden\",\"type\":\"recreational\",\"participants\":1,\"price\":0.3,\"link\":\"\",\"key\":\"1934228\",\"accessibility\":0.35}",
        "monitoredEndpointId": 3
    },
    {
        "id": 18,
        "dateOfCheck": "2022-05-30T12:36:11.65922Z",
        "httpStatusCode": 200,
        "payload": "{\"activity\":\"Go to a local thrift shop\",\"type\":\"recreational\",\"participants\":1,\"price\":0.1,\"link\":\"\",\"key\":\"8503795\",\"accessibility\":0.2}",
        "monitoredEndpointId": 3
    }
]
```

### Create a new endpoint to monitor

```
POST /endpoints
```

The details of the endpoint to monitor should be provided in the request body,
as per the following example.

Example request:

```
POST /endpoints
```

```json
{
    "url":"http://www.boredapi.com/api/activity?type=recreational",
    "interval":10
}
```

Example response:

```json
{
    "id": 3,
    "url": "http://www.boredapi.com/api/activity?type=recreational",
    "lastChecked": null,
    "interval": 10,
    "created": "2022-05-30T12:50:39.891990108Z"
}
```

### Get the results from a monitored endpoint

```
GET /results
Parameter: id -- The ID of an existing endpoint
```

Example request:

```
GET /results?id=3
```

Example response:

```json
[
    {
        "id": 36,
        "dateOfCheck": "2022-05-30T12:52:15.659344Z",
        "httpStatusCode": 200,
        "payload": "{\"activity\":\"Learn and play a new card game\",\"type\":\"recreational\",\"participants\":1,\"price\":0,\"link\":\"https://www.pagat.com\",\"key\":\"9660022\",\"accessibility\":0}",
        "monitoredEndpointId": 3
    },
    {
        "id": 34,
        "dateOfCheck": "2022-05-30T12:52:00.379698Z",
        "httpStatusCode": 200,
        "payload": "{\"activity\":\"Go to the gym\",\"type\":\"recreational\",\"participants\":1,\"price\":0.2,\"link\":\"\",\"key\":\"4387026\",\"accessibility\":0.1}",
        "monitoredEndpointId": 3
    }
]
```

### Deleting a monitored endpoint

```
DELETE /endpoints
Parameter: id -- The ID of an existing endpoint
```

Example request:

```
DELETE /endpoints?id=3
```

If the deletion was successful, the API will return **200 OK** with an empty body,
otherwise the body will contain details about the error.

## Running

This app relies on Docker Compose. Consequently, after cloning the repository,
the following command should be sufficient to set up and run a fully-operational
instance of the app:

```shell
docker compose up --build
```