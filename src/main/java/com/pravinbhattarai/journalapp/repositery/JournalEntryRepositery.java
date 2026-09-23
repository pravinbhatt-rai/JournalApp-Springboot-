package com.pravinbhattarai.journalapp.repositery;

import com.pravinbhattarai.journalapp.entity.JournelEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepositery extends MongoRepository<JournelEntry, ObjectId> {

}
