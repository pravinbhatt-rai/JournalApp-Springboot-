package com.pravinbhattarai.journalapp.service;

import com.pravinbhattarai.journalapp.entity.UserEntry;
import com.pravinbhattarai.journalapp.repositery.UserEntryRepositery;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserEntryRepositery userEntryRepositery;
    @Override
    public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException {
        UserEntry user=userEntryRepositery.findByUserName(username).orElse(null);

        if(user!=null){

            return User.builder()
                     .username(user.getUserName())
                     .password(user.getPassword())
                     .roles(user.getRoles().toArray(new String[0]))
                     .build();
        }
        throw  new UsernameNotFoundException("User no found"+username);

    }
}
