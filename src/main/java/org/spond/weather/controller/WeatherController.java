package org.spond.weather.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spond.weather.dto.WeatherResponse;
import org.spond.weather.exception.RateLimitExceededException;
import org.spond.weather.service.WeatherService;
import org.spond.weather.util.SlidingWindowLogRateLimiter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private static final Logger log = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;
    private final SlidingWindowLogRateLimiter rateLimiter;

    @GetMapping("/forecast")
    public ResponseEntity<WeatherResponse> getWeather(
            @Valid @RequestParam("lat") double latitude,
            @Valid @RequestParam("lon") double longitude,
            @RequestParam("sTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartTime
    ) {
        if (eventStartTime.isAfter(LocalDateTime.now()) && eventStartTime.isBefore(LocalDateTime.now().plusDays(7))) {
            if (rateLimiter.tryAcquire()) {
                WeatherResponse weatherResponse = weatherService.getWeatherForecast(latitude, longitude, eventStartTime);
                return ResponseEntity.ok(weatherResponse);
            } else {
                throw new RateLimitExceededException("Too many requests - rate limit exceeded. Please try again later.");
            }
        } else {
            throw new IllegalArgumentException("Event Start time must be within 7 days");
        }
    }
}
