package com.pravinbhattarai.journalapp.service;

import com.pravinbhattarai.journalapp.api.response.WeatherResponse;
import com.pravinbhattarai.journalapp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${weatherstack.api-key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;


    public WeatherResponse getWeather(String City){
    String finalApi=appCache.App_cache
            .get("weather_api")
            .replace("CITY",City)
            .replace("API_KEY",apiKey);

    ResponseEntity<WeatherResponse>response=restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);


        return response.getBody();
    }



}
