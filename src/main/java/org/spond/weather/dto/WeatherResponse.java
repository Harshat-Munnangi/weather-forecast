package org.spond.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private double temperature;
    private double windSpeed;
    private LocalDateTime eventTime;
    private LocalDateTime lastUpdated;
}
