package com.pravinbhattarai.journalapp.service;

import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.repositery.UserEntryRepositery;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserEntryRepositery userEntryRepositery;

    @Autowired
    private MongoTemplate mongoTemplate;


    // CREATE USER
    public void createUser(UserEntry user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("USER"));
        }

        userEntryRepositery.save(user);
    }

    public UserEntry createAdmin(UserEntry userEntry){
        userEntry.setRoles(List.of("USER","ADMIN"));

        return userEntryRepositery.save(userEntry);
    }




    // SAVE USER
    public void SaveEntry(UserEntry user) {
        try {
            userEntryRepositery.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // GET ALL USERS
    public List<UserEntry> getAll() {
        return userEntryRepositery.findAll();
    }


    // GET USER BY ID
    public Optional<UserEntry> getById(ObjectId id) {
        return userEntryRepositery.findById(id);
    }


    // GET USER BY USERNAME
    public UserEntry findByUserName(String userName) {
        return userEntryRepositery
                .findByUserName(userName)
                .orElse(null);
    }


    // DELETE USER BY ID
    public void deleteById(ObjectId id) {
        userEntryRepositery.deleteById(id);
    }


    // FIND USER BY ID
    public UserEntry findById(ObjectId myId) {
        return userEntryRepositery
                .findById(myId)
                .orElse(null);
    }


    // UPDATE USER BY ID
    public boolean updateById(
            ObjectId id,
            String userName,
            String password) {

        Query query = new Query(
                Criteria.where("_id").is(id)
        );

        Update update = new Update();

        if (userName != null) {
            update.set("userName", userName);
        }

        if (password != null) {
            update.set("password", password);
        }

        var result = mongoTemplate.updateFirst(
                query,
                update,
                UserEntry.class
        );

        return result.getMatchedCount() > 0;
    }
}