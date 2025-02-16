package org.perscholas.cafe_mvp.config;


import org.perscholas.cafe_mvp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login", "/register").permitAll()
                        .requestMatchers("/cart**").hasRole("CUSTOMER")
                        .requestMatchers("/menu/**").hasRole("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/menu", true)
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .permitAll()
//                        .successHandler((request, response, authentication) -> {
//                            authentication.getAuthorities().forEach(grantedAuthority -> {
//                                String role = grantedAuthority.getAuthority();
//
//                                try {
//                                    if (role.equals("ROLE_CUSTOMER")) {
//                                        response.sendRedirect("/menu");
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            });
//                        })

                   )

                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll())
                .userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
