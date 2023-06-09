package com.wassimsakri.springsecurity.sec.contoller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wassimsakri.springsecurity.sec.JWTUtil;
import com.wassimsakri.springsecurity.sec.entity.AppRole;
import com.wassimsakri.springsecurity.sec.entity.AppUser;
import com.wassimsakri.springsecurity.sec.service.AccountService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path ="/user")
    @PostAuthorize("hasAuthority('USER')")
    public List<AppUser> appUsers() {
        return accountService.listUsers();
    }

    @PostMapping(path = "/user")
    @PostAuthorize("hasAuthority('ADMIN')")
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
    @PostAuthorize("hasAuthority('ADMIN')")
    public AppRole saveRole(@RequestBody AppRole appRole){
        return  accountService.addNewRole(appRole);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserFrom roleUserFrom){
        accountService.addRoleToUser(roleUserFrom.getUserName(),roleUserFrom.getRoleName());
    }
    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String auhToken  = request.getHeader("Authorization");

        if(auhToken!=null && auhToken.startsWith("Bearer ")){
            try {
                String jwt=auhToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username= decodedJWT.getSubject();
                AppUser appUser= accountService.loadUserByUsername(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis()+1*60*1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", appUser.getAppRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> idToken = new HashMap<>();
                idToken.put("access-token",jwtAccessToken);
                idToken.put("refresh-token",jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);
            }catch (Exception e){
                throw e ;
            }
        }else{
            throw new RuntimeException("Rfersh token required !!!");
        }

    }

    @GetMapping(path = "/profile")
    public AppUser profile(Principal principal){
        return accountService.loadUserByUsername(principal.getName());
    }
}