package com.dimsen.miniproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UsersConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}password")
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$12$l6oStw79s6aCkjReZ4AWRuCjwDDfEqXzW197sy6QnDhvpGX3Xq216")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}
