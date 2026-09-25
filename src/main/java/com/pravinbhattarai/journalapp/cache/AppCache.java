package com.pravinbhattarai.journalapp.cache;

import com.pravinbhattarai.journalapp.entity.ConfigJournalAppEntity;
import com.pravinbhattarai.journalapp.repositery.ConfigJournalAppRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public Map<String, String> App_cache;


    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;

    @PostConstruct
    public void init() {
        App_cache = new HashMap<>();

        List<ConfigJournalAppEntity> all =
                configJournalAppRepo.findAll();

        for (ConfigJournalAppEntity config : all) {
            App_cache.put(config.getKey(), config.getVal());
        }

        System.out.println("App cache init"+App_cache);    }
}