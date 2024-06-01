package com.api.taskmanager.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors().and().csrf()
//                .disable().authorizeHttpRequests()
//                .antMatchers(HttpMethod.POST, "/users").permitAll()
//                .antMatchers(HttpMethod.POST, "/users/reset-password").permitAll()
//                .antMatchers(HttpMethod.PUT, "/users/reset-password/**").permitAll()
//                .antMatchers(HttpMethod.PUT,"/board-invitation/accept-invite/**").permitAll()
//                .and().httpBasic()
//                .and()
//                .authorizeHttpRequests()
//                .anyRequest().authenticated();
//        http.cors((cors) -> cors.disable());
        http.csrf((csrf) -> csrf.disable());
        http.httpBasic(Customizer.withDefaults()).authorizeHttpRequests(((authorize) -> authorize
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
