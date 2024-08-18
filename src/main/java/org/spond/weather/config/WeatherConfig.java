package org.spond.weather.config;

import org.spond.weather.util.SlidingWindowLogRateLimiter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WeatherConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .defaultHeader(HttpHeaders.USER_AGENT, "acmeweathersite.com support@acmeweathersite.com")
                .build();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("weatherCache");
    }

    @Bean
    public SlidingWindowLogRateLimiter slidingWindowLogRateLimiter() {
        return new SlidingWindowLogRateLimiter(80, 1000);
    }

}
