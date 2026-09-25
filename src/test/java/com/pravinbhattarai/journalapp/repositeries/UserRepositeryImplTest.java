package com.pravinbhattarai.journalapp.repositeries;

import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.repositery.UserRepositeryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootTest
public class UserRepositeryImplTest {
    @Autowired
    private UserRepositeryImpl userRepositeryImpl;

    @Test
    @Disabled("tested")
    void testSaveNewUser() {
          List<UserEntry> users =
                userRepositeryImpl.getUsersForSa();

        Assertions.assertFalse(users.isEmpty());
    }
}
