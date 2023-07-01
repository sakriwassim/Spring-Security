package com.wassimsakri.springsecurity.sec.service;

import com.wassimsakri.springsecurity.sec.entity.AppRole;
import com.wassimsakri.springsecurity.sec.entity.AppUser;

import java.util.List;

public interface AccountService {

    AppUser loadUserByUsername(String username);
    AppRole getRoleByname(String rolename);
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String userName , String roleName);
    List<AppUser>listUsers();


}
