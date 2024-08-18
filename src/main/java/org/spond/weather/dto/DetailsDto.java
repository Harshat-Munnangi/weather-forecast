package org.spond.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsDto {
    @JsonProperty("air_temperature")
    private double airTemperature;
    @JsonProperty("wind_speed")
    private double windSpeed;
}
