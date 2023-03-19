package com.wassimsakri.springsecurity.sec.service;

import com.wassimsakri.springsecurity.sec.entity.AppRole;
import com.wassimsakri.springsecurity.sec.entity.AppUser;
import com.wassimsakri.springsecurity.sec.repo.AppRoleRepository;
import com.wassimsakri.springsecurity.sec.repo.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }


    @Override
    public AppUser addNewUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser appUser = appUserRepository.findByUserName(userName);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);

    }

    @Override
    public AppUser loadUserByUsername(String username) {

        return appUserRepository.findByUserName(username);

    }

    @Override
    public List<AppUser> listUser() {
        return appUserRepository.findAll();
    }
}
