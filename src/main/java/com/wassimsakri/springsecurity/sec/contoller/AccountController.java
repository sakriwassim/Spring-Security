package com.wassimsakri.springsecurity.sec.contoller;


import com.wassimsakri.springsecurity.sec.entity.AppRole;
import com.wassimsakri.springsecurity.sec.entity.AppUser;
import com.wassimsakri.springsecurity.sec.service.AccountService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/user")
    public AppUser saveUser(@RequestBody AppUser appUser){
        return  accountService.addNewUser(appUser);
    }

    @GetMapping("/user/{name}")
    public AppUser GetUserByName(@PathVariable("name") String name){
        return  accountService.loadUserByUsername(name);
    }

    @GetMapping("/role/{name}")
    public AppRole GetRoleByName(@PathVariable("name") String name){
        return  accountService.getRoleByname(name);
    }

    @PostMapping(path = "/role")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return  accountService.addNewRole(appRole);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserFrom roleUserFrom){
          accountService.addRoleToUser(roleUserFrom.getUserName(),roleUserFrom.getRoleName());
    }


}