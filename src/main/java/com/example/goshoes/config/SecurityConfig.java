package com.example.goshoes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.goshoes.controller.UserInfoController;
import com.example.goshoes.model.UserInfoRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
	
//	@Bean
//	public UserInfoController userDetailsService() {
//		return new UserInfoController();
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//        .authorizeHttpRequests(authorize -> authorize
//            .requestMatchers("/", "/home", "/shoes/**", "/css/**", "/js/**").permitAll()  
//            .requestMatchers("/admin/**").hasRole("ADMIN")
//            .anyRequest().authenticated()
//        )
//        .formLogin(form -> form
//            .loginPage("/login")  
//            .permitAll()  
//            .defaultSuccessUrl("/", true) 
//        )
//        .logout(logout -> logout
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/")  
//            .permitAll()
//        )
//        .exceptionHandling()
//            .accessDeniedPage("/403"); 
		
//
//
//		return http.build();
		http
        .csrf().disable()
        .authorizeRequests()
        .requestMatchers("/api/login", "/api/signup").permitAll() // 로그인과 회원가입은 모두에게 허용
        .anyRequest().authenticated()
        .and()
        .httpBasic();
    
		http
	        .authorizeHttpRequests(authorize -> authorize
        		.requestMatchers("/admin/**").hasRole("ADMIN")
        		.anyRequest().permitAll()
	       
	        )
	        .csrf().disable()  
	        .headers().frameOptions().disable()  
	        .and()
	        .formLogin().disable()  
	        .httpBasic().disable(); 
	       
	
	    return http.build();
    }
}
