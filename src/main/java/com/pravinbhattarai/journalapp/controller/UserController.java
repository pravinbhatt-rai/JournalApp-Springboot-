package com.pravinbhattarai.journalapp.controller;

import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserEntryService userEntryService;

    private static final Logger logger= LoggerFactory.getLogger(UserController.class);




    @GetMapping
    public List<UserEntry> getAll(){
        return userEntryService.getAll();
    }
    @GetMapping("/{userName}")
    public ResponseEntity<UserEntry> getUserByUserName(
            @PathVariable String userName) {

        UserEntry user =
                userEntryService.findByUserName(userName);

        if (user == null) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                user,
                HttpStatus.OK
        );
    }

    @PostMapping
    public Boolean createUser(@RequestBody UserEntry myEntry){
        try {
            userEntryService.createUser(myEntry);
            return true;

        } catch (Exception e) {
            logger.info("Duplicate username");
            return  false;
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<UserEntry> getUserEntryById(@PathVariable ObjectId myId){
        return ResponseEntity.of(userEntryService.getById(myId));
    }


    @DeleteMapping ("/id/{myId}")
    public ResponseEntity<Boolean> deleteuserEntryById(@PathVariable ObjectId myId){
          userEntryService.deleteById(myId);
          return new ResponseEntity<>(true,HttpStatus.OK);

    }
    @PutMapping("id/{myId}")
    public ResponseEntity<?>updateUser(@PathVariable ObjectId myId, @RequestBody UserEntry user){
        UserEntry userInDB=userEntryService.findById(myId);
        if(userInDB==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(user.getPassword());
            userEntryService.SaveEntry(userInDB);
        return new ResponseEntity<>(userInDB,HttpStatus.OK);

    }
//
@PatchMapping("/id/{myId}")
public ResponseEntity<?> updateuserEntry(
        @PathVariable ObjectId myId,
        @RequestBody Map<String, Object> updates
        ) {

        String userName=(String) updates.get("userName");
        String passWord=(String) updates.get("password");

   boolean Updated= userEntryService.updateById(myId, userName,passWord);
    if(!Updated){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return  new ResponseEntity<>(HttpStatus.OK);
}

}
