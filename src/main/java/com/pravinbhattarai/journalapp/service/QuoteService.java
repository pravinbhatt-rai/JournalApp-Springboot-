package com.pravinbhattarai.journalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClient;

public class QuoteService {
    private  final RestClient restClient;

    public QuoteService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://dummyjson.com").build();
    }
    public String getRandomQuotes(){
        return  restClient.get().uri("/quotes/random").retrieve().body(String.class);
    }
}
