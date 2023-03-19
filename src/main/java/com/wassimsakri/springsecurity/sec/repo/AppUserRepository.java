package com.wassimsakri.springsecurity.sec.repo;

import com.wassimsakri.springsecurity.sec.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByUserName(String appUser);

}
