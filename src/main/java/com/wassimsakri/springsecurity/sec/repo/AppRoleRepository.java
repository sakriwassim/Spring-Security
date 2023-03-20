package com.wassimsakri.springsecurity.sec.repo;
import com.wassimsakri.springsecurity.sec.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
AppRole findByRoleName(String roleName);

}
