package com.pravinbhattarai.journalapp.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.pravinbhattarai.journalapp.enums.SentimentEnums;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "title",
        "content",
        "date"
})
@Document(collection = "journalEntry")
@Data
public class JournelEntry {

    @Id
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;

    private SentimentEnums sentiment;
}