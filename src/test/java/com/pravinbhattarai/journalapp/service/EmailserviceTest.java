package com.pravinbhattarai.journalapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

public class EmailserviceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmail() {
        emailService.sendEmail("bhattarai9366@gmail.com","test mail","app kaise haie bhaisab aapki maah ka barosaa");
    }
}
