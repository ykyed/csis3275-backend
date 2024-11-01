package com.example.goshoes.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.goshoes.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final CustomUserDetailsService userDetailsService;
	private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CustomUserDetailsService userDetailsService, CorsConfigurationSource corsConfigurationSource) {
        this.userDetailsService = userDetailsService;
        this.corsConfigurationSource = corsConfigurationSource;
    }
	    
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.cors().configurationSource(corsConfigurationSource)
		.and()
        .csrf().disable()
        .headers(headers -> headers.frameOptions().disable())
        .authorizeRequests()
            .requestMatchers("/login", "/api/**").permitAll()
            .requestMatchers("/admin", "/admin/**").hasAuthority("ADMIN")
            .anyRequest().permitAll()
           .and()
        .formLogin()  
            .usernameParameter("username") 
            .passwordParameter("password") 
            .successHandler(authenticationSuccessHandler()) 
            .failureHandler(authenticationFailureHandler()) 
            .permitAll()
        .and()
        .logout()
            .logoutUrl("/logout") 
            .logoutSuccessUrl("/") 
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
		.and()
		.exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler());


     return http.build();
    }

	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
	
    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"success\", \"message\": \"Login successful\"}");
        };
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Login failed\"}");
        };
    }
    
    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/");
        };
    }
}
