package com.hackerrank.assignment.config;

import com.hackerrank.assignment.security.JwtAuthorizationTokenFilter;
import com.hackerrank.assignment.security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Security Configuration class
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable().formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .authorizeRequests().antMatchers("/authenticate").permitAll().
                anyRequest().authenticated().and().
                exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


}
