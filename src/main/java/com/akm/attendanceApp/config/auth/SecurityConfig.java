package com.akm.attendanceApp.config.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler authenticationSuccessHandler) throws Exception {

        http.httpBasic( basic -> basic
                .authenticationEntryPoint((request, response, authException) -> {
                    if(request.getHeader("Accept").contains("text/html")) {
                        response.sendRedirect("/login");
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
                    }
                })

        );
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/bootstrap/**","/bootstrap/css/**","/bootstrap/js/**", "/static/**").permitAll()
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/error","/api/auth/**").permitAll()
                        .requestMatchers(
                                "/admin/**",
                                "/admin/user/**",
                                "/api/v1/admin/user/**",
                                "/api/v1/admin/attendance/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                "/user/**",
                                "/api/v1/user/attendance/**"
                        ).hasRole("USER")
                        .anyRequest().authenticated()
                );
        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler)
                        .permitAll()
                );
    return http.build();
    }
}
