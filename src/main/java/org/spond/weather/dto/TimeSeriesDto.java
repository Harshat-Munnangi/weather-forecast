package org.spond.weather.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSeriesDto {

    @JsonProperty("time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private LocalDateTime time;

    @JsonProperty("data")
    private DataDetails data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataDetails {
        private Instant instant;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Instant {
            private DetailsDto details;
        }
    }
}
