package com.pravinbhattarai.journalapp.service;

import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.repositery.UserEntryRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Admin {

    @Autowired
    private UserEntryRepositery userEntryRepositery;

    public UserEntry makeAdmin(String userName){
        UserEntry user=userEntryRepositery.findByUserName(userName).orElseThrow(()->new RuntimeException("User Not found"));
        if(!user.getRoles().contains("ADMIN")){
            user.getRoles().add("ADMIN");
        }
        return  userEntryRepositery.save(user);
    }
}
