package com.pravinbhattarai.journalapp.controller;

import com.pravinbhattarai.journalapp.entity.JournelEntry;
import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.service.JournalEntryService;
import com.pravinbhattarai.journalapp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserEntryService userEntryService;


    // GET ALL JOURNAL ENTRIES
    @GetMapping
    public ResponseEntity<List<JournelEntry>> getAll() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String userName=authentication.getName();
        UserEntry user=userEntryService.findByUserName(userName);

        List<JournelEntry>all=user.getJournelEntries();

        if(all!=null&&!all.isEmpty()) {


            return new ResponseEntity<>(
                   all,
                    HttpStatus.OK
            );
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // GET ALL JOURNAL ENTRIES OF A USER BY USERNAME
    //
    // GET /journal/pravin
    //
//    @GetMapping("/{userName}")
//    public ResponseEntity<?> getJournalEntriesOfUser(
//           ) {
//
//        UserEntry user =
//                userEntryService.findByUserName(userName);
//
//        if (user == null) {
//            return new ResponseEntity<>(
//                    "User not found",
//                    HttpStatus.NOT_FOUND
//            );
//        }
//
//        List<JournelEntry> all =
//                user.getJournelEntries();
//
//        if (all != null && !all.isEmpty()) {
//
//            return new ResponseEntity<>(
//                    all,
//                    HttpStatus.OK
//            );
//        }
//
//        return new ResponseEntity<>(
//                "No journal entries found",
//                HttpStatus.NOT_FOUND
//        );
//    }


    // CREATE JOURNAL ENTRY FOR USERNAME
    //
    // POST /journal/pravin
    //
    @PostMapping()
    public ResponseEntity<?> createEntry(
            @RequestBody JournelEntry myEntry) {

        try {

            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            assert authentication != null;
            String userName=authentication.getName();
            journalEntryService.saveEntry(
                    myEntry,
                    userName
            );

            return new ResponseEntity<>(
                    myEntry,
                    HttpStatus.CREATED
            );

        } catch (RuntimeException e) {

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Unable to create journal entry",
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    // GET SINGLE JOURNAL ENTRY BY JOURNAL ID
    //
    // GET /journal/id/68abc...
    //
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournelEntry> getJournalEntryById(
            @PathVariable ObjectId myId) {

        return ResponseEntity.of(
                journalEntryService.getById(myId)
        );
    }


    // DELETE JOURNAL ENTRY
    //
    // DELETE /journal/pravin/68abc...
    //
    @DeleteMapping("/{journalId}")
    public ResponseEntity<?> deleteJournalEntryById(
            @PathVariable ObjectId journalId) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String userName=authentication.getName();

        boolean deleted =
                journalEntryService.deleteById(
                        userName,
                        journalId
                );

        if (!deleted) {
            return new ResponseEntity<>(
                    false,
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                true,
                HttpStatus.OK
        );
    }


    // PATCH JOURNAL ENTRY
    //
    // PATCH /journal/pravin/68abc...
    //
    @PatchMapping("/{userName}/{journalId}")
    public ResponseEntity<?> updateJournalEntry(
            @PathVariable String userName,
            @PathVariable ObjectId journalId,
            @RequestBody Map<String, Object> updates) {

        boolean updated =
                journalEntryService.updateById(
                        userName,
                        journalId,
                        updates
                );

        if (!updated) {

            return new ResponseEntity<>(
                    false,
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                true,
                HttpStatus.OK
        );
    }
}