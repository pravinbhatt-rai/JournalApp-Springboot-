package com.pravinbhattarai.journalapp.service;

import com.pravinbhattarai.journalapp.entity.JournelEntry;
import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.repositery.JournalEntryRepositery;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepositery journalEntryRepositery;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserEntryService userEntryService;



    // CREATE JOURNAL ENTRY FOR USERNAME
    @Transactional
    public void saveEntry(
            JournelEntry journelEntry,
            String userName) {

        UserEntry user = userEntryService.findByUserName(userName);

        if (user == null) {
            log.info("user not found");
        }

        journelEntry.setDate(LocalDateTime.now());

        JournelEntry save =
                journalEntryRepositery.save(journelEntry);

        user.getJournelEntries().add(save);

        userEntryService.SaveEntry(user);
    }


    // GET ALL JOURNAL ENTRIES
    public List<JournelEntry> getAll() {
        return journalEntryRepositery.findAll();
    }


    // GET JOURNAL ENTRY BY JOURNAL ID
    public Optional<JournelEntry> getById(ObjectId id) {
        return journalEntryRepositery.findById(id);
    }


    // DELETE JOURNAL ENTRY USING USERNAME + JOURNAL ID
    public boolean deleteById(
            String userName,
            ObjectId journalId) {

        UserEntry user =
                userEntryService.findByUserName(userName);

        if (user == null) {
            return false;
        }

        boolean removed =
                user.getJournelEntries()
                        .removeIf(
                                x -> x.getId().equals(journalId)
                        );

        if (!removed) {
            return false;
        }

        userEntryService.SaveEntry(user);

        journalEntryRepositery.deleteById(journalId);

        return true;
    }


    // UPDATE JOURNAL ENTRY USING USERNAME + JOURNAL ID
    public boolean updateById(
            String userName,
            ObjectId journalId,
            Map<String, Object> updates) {

        UserEntry user =
                userEntryService.findByUserName(userName);

        if (user == null) {
            return false;
        }

        // Check whether this journal belongs to this user
        boolean belongsToUser =
                user.getJournelEntries()
                        .stream()
                        .anyMatch(
                                x -> x.getId().equals(journalId)
                        );

        if (!belongsToUser) {
            return false;
        }

        Query query = new Query(
                Criteria.where("_id").is(journalId)
        );

        Update update = new Update();

        if (updates.containsKey("title")) {
            update.set(
                    "title",
                    updates.get("title")
            );
        }

        if (updates.containsKey("content")) {
            update.set(
                    "content",
                    updates.get("content")
            );
        }

        if (update.getUpdateObject().isEmpty()) {
            return false;
        }

        var result = mongoTemplate.updateFirst(
                query,
                update,
                JournelEntry.class
        );

        return result.getMatchedCount() > 0;
    }
}