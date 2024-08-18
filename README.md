# weather-forecast

This project is a simplified weather-forecast API with a single endpoint that shows the weather forecasts for your upcoming events.

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Setup](#setup)
- [Endpoint](#endpoint)
- [Sample Request](#sample-request)
- [Sample Success Response](#sample-success-response)
- [Sample Error Response](#sample-error-response)
- [Testing](#testing)

## Features

- Forecast endpoint for fetching the weather details of your upcoming events.
- Caching of weather details for 2 hours TTL
- Rate Limiter with max 80 request per second
- Exception handling for common scenarios.

## Requirements

- Java 17
- Gradle

## Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/Harshat-Munnangi/weather-forecast.git
   ```

2. Build the project:

   ```bash
   cd weather-forecast
   ./gradlew build
   ```

3. Run the application:

   ```bash
   java -jar build/libs/weather-0.0.1-SNAPSHOT.jar
   ```

   The API will be accessible at `http://localhost:8080`.

## Endpoint

### Checkout

- **Method:** GET
- **URL:** http://localhost:8080/api/weather/forecast
- **Headers:**
    - Accept: application/json
    - Content-Type: application/json
- **Request Params:**
    - latitude
    - longitude
    - eventStartTime
- **Response:**
    - JSON with the air temperature, wind speed

## Sample Request

```json
GET http://localhost:8080/api/weather/forecast?lat=68.92&lon=66.87&sTime=2024-08-21T19:06:02
Headers:
  Accept: application/json
  Content-Type: application/json
```
## Sample Request (cURL)

You can use `curl` to make requests to the API from the command line. Here's an example:

```bash
curl -X GET http://localhost:8080/api/weather/forecast?lat=68.92&lon=66.87&sTime=2024-08-21T19:06:02 \
  -H "Accept: application/json" \
  -H "Content-Type: application/json"'
```

## Sample Success Response

```json
HTTP Status: 200 OK
Headers:
  Content-Type: application/json
Body:
    {
    "temperature": 7.1,
    "windSpeed": 7.8,
    "eventTime": "2024-08-21T18:00:00",
    "lastUpdated": "2024-08-18T19:17:15"
    }
```

## Sample Error Response

```json
HTTP Status: 400 Bad Request
Body:
{
    "status": 400,
    "error": "Unexpected error",
    "message": "Too many requests - rate limit exceeded. Please try again later.",
    "timestamp": "2024-08-18T17:38:45"
}
```
## Testing

To run tests, use the following command:

```bash
./gradlew test
```