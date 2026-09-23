//package com.pravinbhattarai.journalapp.controller;
//
//import com.pravinbhattarai.journalapp.entity.JournelEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryController {
//
//    private  Map<Long, JournelEntry> journelEntryMap=new HashMap<>();
//
//    @GetMapping
//    public List<JournelEntry> getAll(){
//        return new ArrayList<>(journelEntryMap.values());
//    }
//
//    @PostMapping
//    public Boolean createEntry(@RequestBody JournelEntry myEntry){
//        return true;
//
//    }
//
//    @GetMapping("/id/{myId}")
//    public JournelEntry getJournelEntryById(@PathVariable Long myId){
//        return journelEntryMap.get(myId);
//
//    }
//
//
//    @DeleteMapping ("/id/{myId}")
//    public JournelEntry deleteJournelEntryById(@PathVariable Long myId){
//        return journelEntryMap.remove(myId);
//
//    }
//
//    @PutMapping("/id/{myId}")
//    public JournelEntry putJournelEntry(@PathVariable Long myId,@RequestBody JournelEntry myEntry){
//        return journelEntryMap.put(myId, myEntry);
//    }
//
//}
