package com.pravinbhattarai.journalapp.repositery;

import com.pravinbhattarai.journalapp.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserEntryRepositery extends MongoRepository<UserEntry, ObjectId> {
Optional<UserEntry> findByUserName(String userName);
}
