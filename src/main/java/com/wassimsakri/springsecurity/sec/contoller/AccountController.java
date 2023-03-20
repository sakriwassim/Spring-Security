package com.wassimsakri.springsecurity.sec.contoller;


import com.wassimsakri.springsecurity.sec.entity.AppUser;
import com.wassimsakri.springsecurity.sec.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path ="/user")
    public List<AppUser> appUsers() {
        return accountService.listUsers();
    }


}
