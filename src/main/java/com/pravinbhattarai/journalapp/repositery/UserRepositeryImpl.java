package com.pravinbhattarai.journalapp.repositery;

import com.pravinbhattarai.journalapp.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserRepositeryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
    public List<UserEntry> getUsersForSa() {
        Query query=new Query();
//        query.addCriteria(Criteria.where("email").exists(true).ne(null).ne(""));
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<UserEntry> userEntries = mongoTemplate.find(query, UserEntry.class);
        return userEntries;



    }
}
