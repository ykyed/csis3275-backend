package com.example.goshoes.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.UserInfo;
import com.example.goshoes.model.UserInfoRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserInfoController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	
	@Autowired
	UserInfoRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signup")
	public ResponseEntity<Boolean> signup(@RequestBody UserInfo userInfo) {
	    logger.info(userInfo.getFirstName());

	    if (repository.findByEmail(userInfo.getEmail()) != null) {
	        return new ResponseEntity<>(false, HttpStatus.CONFLICT);
	    }

	    try {
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	        userInfo.setRole("USER");
	        repository.save(userInfo);
	        return new ResponseEntity<>(true, HttpStatus.OK);
	    } catch (Exception e) {
	        logger.error("Error occurred during signup", e);
	        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers() {
		
		try {
			List<UserInfo> userData = repository.findAll();
			logger.info("user:" + userData.size());
			return new ResponseEntity<>(userData, HttpStatus.OK);
		
		} 
		catch (Exception e) {
			logger.info("e:" + e.getMessage());
		}
		return null;
    }
	
	@GetMapping("/userinfo")
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
		logger.info("authentication:" + authentication);
		try {
			if (authentication != null && authentication.isAuthenticated()) {
	            String role = authentication.getAuthorities().stream()
	                                         .findFirst()
	                                         .map(auth -> auth.getAuthority().replace("ROLE_", ""))
	                                         .orElse("USER");
	            String username = authentication.getName();
	            logger.info("username:" + username);
	            String firstName = repository.findByEmail(username).getFirstName();
	            Map<String, String> info = Map.of("username", username, "role", role, "name", firstName);
	            return new ResponseEntity<>(info, HttpStatus.OK);
	        }
			else {
				Map<String, String> info = Map.of("username", "", "role", "", "name", "");
	            return new ResponseEntity<>(info, HttpStatus.OK);
			}
		}
		catch (Exception e) {
			logger.info("e:" + e.getMessage());
			return new ResponseEntity<>("Failed to getCurrentUserInfo.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}

	
