package com.pravinbhattarai.journalapp.controller;

import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.service.Admin;
import com.pravinbhattarai.journalapp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private Admin admin;
    @GetMapping("/all-users")
    public ResponseEntity<List<UserEntry>> getAllUserByAdmin() {


        List<UserEntry> all = userEntryService.getAll();
        if(!all.isEmpty()&& all!=null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin/{username}")
    public ResponseEntity<?> createAdmin(@PathVariable String username) {

        UserEntry saveUser = admin.makeAdmin(username);

        return ResponseEntity.ok(saveUser);
    }


}
