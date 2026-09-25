package com.pravinbhattarai.journalapp.controller;


import com.pravinbhattarai.journalapp.api.response.WeatherResponse;
import com.pravinbhattarai.journalapp.cache.AppCache;
import com.pravinbhattarai.journalapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/getweather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private AppCache appCache;

    @GetMapping
    public ResponseEntity<?>getWeather(){


        WeatherResponse weather=weatherService.getWeather("mumbai");
        String Greetings="";


        if(weather!=null){
            Greetings=" Weather feels like" +weather.getCurrent().getFeelsLike();
        }
        return  new ResponseEntity<>(Greetings, HttpStatus.OK);
    }

    @GetMapping("/clear_app-cache")
    public void clearCache(){
        appCache.init();
    }
}
