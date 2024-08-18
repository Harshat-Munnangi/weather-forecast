package org.spond.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.spond.weather.dto.WeatherResponse;
import org.spond.weather.exception.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSuccessfullyFetchEventWeather() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/weather/forecast?lat=68.92&lon=66.87&sTime=2024-08-21T19:06:02")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        WeatherResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), WeatherResponse.class);
        assertThat(response).isNotNull();
        assertThat(response.getEventTime()).isAfter(LocalDateTime.now());
    }

    @Test
    void shouldThrowErrorToFetchEventWeather() throws Exception {
        LocalDateTime eventAfterSevenDays = LocalDateTime.now().plusDays(9);
        MvcResult result = mockMvc.perform(get("/api/weather/forecast?lat=68.92&lon=66.87&sTime="+ eventAfterSevenDays)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        ErrorResponse error = objectMapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(error.getStatus()).isEqualTo(400);
    }
}
