package com.wassimsakri.springsecurity.sec;
import com.wassimsakri.springsecurity.sec.filters.JwtAuthenticationFilter;
import com.wassimsakri.springsecurity.sec.filters.JwtAuthorizationFilter;
import com.wassimsakri.springsecurity.sec.service.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImp userDetailsService;

    public SecurityConfig(UserDetailsServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Disable CSRF protection
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable(); // Allow rendering of frames
//        http.formLogin(); // Enable form-based login
//        http.authorizeRequests().antMatchers(HttpMethod.POST,"/user/**").hasAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/**").hasAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/**").hasAuthority("USER");
        http.authorizeRequests().antMatchers("/h2-console/**", "/refreshToken/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore( new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
