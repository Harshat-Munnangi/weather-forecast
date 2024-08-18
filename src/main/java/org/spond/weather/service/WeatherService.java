package org.spond.weather.service;

import lombok.RequiredArgsConstructor;
import org.spond.weather.dto.TimeSeriesDto;
import org.spond.weather.dto.WeatherDetailsDto;
import org.spond.weather.dto.WeatherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final String apiUrl = "https://api.met.no/weatherapi/locationforecast/2.0/compact";

    private final RestTemplate restTemplate;

    public WeatherResponse getWeatherForecast(double latitude, double longitude, LocalDateTime eventStartTime) {

        WeatherDetailsDto weatherDetailsDto = fetchWeatherDetails(latitude, longitude);
        return weatherResponseMapper(weatherDetailsDto, eventStartTime);
    }

    private WeatherDetailsDto fetchWeatherDetails(double latitude, double longitude) {

        String url = String.format("%s?lat=%.7f&lon=%.6f", apiUrl, latitude, longitude);
        ResponseEntity<WeatherDetailsDto> response = restTemplate.getForEntity(url, WeatherDetailsDto.class);

        return response.getBody();
    }

    private WeatherResponse weatherResponseMapper(WeatherDetailsDto weatherDetailsDto, LocalDateTime eventStartTime) {

        TimeSeriesDto eventWeather = weatherDetailsDto.getProperties().getTimeseries().stream()
                .min(Comparator.comparing(ts -> Duration.between(eventStartTime, ts.getTime()).abs()))
                .orElseThrow(() -> new RuntimeException("Unable to find "));

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setTemperature(eventWeather.getData().getInstant().getDetails().getAirTemperature());
        weatherResponse.setWindSpeed(eventWeather.getData().getInstant().getDetails().getWindSpeed());
        weatherResponse.setEventTime(eventWeather.getTime());
        weatherResponse.setLastUpdated(weatherDetailsDto.getProperties().getMeta().getUpdatedAt());

        return weatherResponse;
    }
}