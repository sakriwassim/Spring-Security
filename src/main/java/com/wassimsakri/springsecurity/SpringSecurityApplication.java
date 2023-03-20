package com.wassimsakri.springsecurity;

import com.wassimsakri.springsecurity.sec.entity.AppRole;
import com.wassimsakri.springsecurity.sec.entity.AppUser;
import com.wassimsakri.springsecurity.sec.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService) {
        return args -> {
            accountService.addNewRole(new AppRole(null, "USER"));
            accountService.addNewRole(new AppRole(null, "ADMIN"));

            accountService.addNewUser(new AppUser(null, "user1", "123", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "user2", "123", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "user3", "123", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "user4", "123", new ArrayList<>()));


        };
    }

}
