package com.pravinbhattarai.journalapp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "userEntry")
@Data
public class UserEntry {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String userName;

    private String password;

    @DBRef
    private List<JournelEntry> journelEntries = new ArrayList<>();

    private List<String> roles;


    public UserEntry(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}