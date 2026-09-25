package com.pravinbhattarai.journalapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_jornal_app")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {
    private String key;

    private  String val;
}
