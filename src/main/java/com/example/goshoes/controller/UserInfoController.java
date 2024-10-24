package com.example.goshoes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public boolean signup(@RequestBody UserInfo userInfo) {
	    logger.info(userInfo.getFirstName());

	    if (repository.findByEmail(userInfo.getEmail()) != null) {
	        return false;
	    }

	    userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	    userInfo.setRole("USER");
	    repository.save(userInfo);
	    return true;
	}

    @PostMapping("/login")
    public String login(@RequestBody UserInfo loginUser) {
        UserInfo user = repository.findByEmail(loginUser.getEmail());

        if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return "login successful";
        }
        return "Invalid email or password";
    }
    
	
	
	@GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers() {
		
		try {
			List<UserInfo> userData = repository.findAll();
			return new ResponseEntity<>(userData, HttpStatus.OK);
		
		} 
		catch (Exception e) {
		}
		return null;
    }
}

	
