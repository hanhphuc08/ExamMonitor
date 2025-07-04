package com.example.exammonitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users", "/users/**").hasRole("ADMIN")
                .requestMatchers("/students", "/students/**", "/examareas", "/examareas/**").hasAnyRole("ADMIN", "INVIGILATOR")
                .requestMatchers("/api/**").hasAnyRole("ADMIN", "INVIGILATOR")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
    
    @Bean
    public InMemoryUserDetailsManager users(PasswordEncoder passwordEncoder) {
        var admin = org.springframework.security.core.userdetails.User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        var gv = org.springframework.security.core.userdetails.User.builder()
                .username("gv")
                .password(passwordEncoder.encode("gv123"))
                .roles("INVIGILATOR")
                .build();

        return new InMemoryUserDetailsManager(admin, gv);
    }

}

