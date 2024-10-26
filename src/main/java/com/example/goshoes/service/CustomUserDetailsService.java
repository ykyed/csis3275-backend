package com.example.goshoes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.goshoes.model.UserInfo;
import com.example.goshoes.model.UserInfoRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
    private final UserInfoRepository userRepository;

    public CustomUserDetailsService(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				
		logger.info("email : " + email);
		UserInfo user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
	        UserDetails info = org.springframework.security.core.userdetails.User.builder()
		            .username(user.getEmail())
		            .password(user.getPassword())
		            .roles(user.getRole())
		            .build();
	        
	        return info;
	}
}
