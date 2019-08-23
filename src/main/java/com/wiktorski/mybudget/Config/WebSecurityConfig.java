package com.wiktorski.mybudget.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
/*The WebSecurityConfig class is annotated with @EnableWebSecurity to enable Spring Security’s web security
 support and provide the Spring MVC integration. It also extends WebSecurityConfigurerAdapter and overrides a
 couple of its methods to set some specifics of the web security configuration.*/

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }




/*The configure(HttpSecurity) method defines which URL paths should be secured and which should not. Specifically,
 the "/" and "/home" paths are configured to not require any authentication. All other paths must be authenticated.*/


    //make sure security policies dont block resources like css or js - look .antMatchers().permitAll()
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/registration", "/css/**", "/js/**", "/assets/**", "/login", "/confirm", "/loginCheckpoint", "/category/**", "/payment/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                //.permitAll()//permitAll nie jest tutaj konieczne bo mozna wyzej w antMatchers dać
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .permitAll();

        /*DODAJ TO
        .antMatchers("/", "/home").permitAll()
        .antMatchers("/admin/**").access("hasRole('ADMIN')")*/
    }
    /*When a user successfully logs in, they will be redirected to the previously requested page that required authentication.
     There is a custom "/login" page specified by loginPage(), and everyone is allowed to view it.*/

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


}
