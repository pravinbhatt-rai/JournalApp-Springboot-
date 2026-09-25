package com.pravinbhattarai.journalapp.scheduler;

import com.pravinbhattarai.journalapp.entity.JournelEntry;
import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.enums.SentimentEnums;
import com.pravinbhattarai.journalapp.model.SendimentData;
import com.pravinbhattarai.journalapp.repositery.UserRepositeryImpl;
import com.pravinbhattarai.journalapp.service.EmailService;
import com.pravinbhattarai.journalapp.service.GetSetiments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class UserScheduler {
    @Autowired
    private UserRepositeryImpl userRepositery;

    @Autowired
    private GetSetiments getSetiments;

    @Autowired
    private EmailService emailService;

    @Autowired
    private KafkaTemplate<String, SendimentData> kafkaTemplate;

    @Scheduled(cron= "0 0 9 * * SUN")
    public void fetchUserAndSendSAEmali(){
        List<UserEntry> users = userRepositery.getUsersForSa();

        for(UserEntry user : users){
            List<JournelEntry> journelEntries = user.getJournelEntries();
            List<SentimentEnums> sentiments = journelEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x->x.getSentiment()).collect(Collectors.toList());

            Map<SentimentEnums,Integer>sentimentscount=new HashMap<>();
            for(SentimentEnums sentiment:sentiments){
                if(sentiment!=null){
                    sentimentscount.put(sentiment,sentimentscount.get(sentiment)+1);

                }
            }

            SentimentEnums mostFrequentSentiment=null;

            int maxCount=0;

            for(Map.Entry<SentimentEnums,Integer> entry:sentimentscount.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount=entry.getValue();
                    mostFrequentSentiment=entry.getKey();

                }
            }

            if(mostFrequentSentiment!=null){
                SendimentData sendimentData= SendimentData.builder().email(user.getEmail()).sentiment("Sentiment for past 7 days is"+ mostFrequentSentiment).build();
                try{
                    kafkaTemplate.send("weekly sentiments", sendimentData.getEmail(),sendimentData);

                }catch (Exception e){
                    log.error("Error while sending email");
                    emailService.sendEmail(sendimentData.getEmail(),"Seniments for previous week",sendimentData.getSentiment());
                }
            }




        }



    }
}
