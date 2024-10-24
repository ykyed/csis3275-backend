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
	
	//궁금증, UserInfoRepository가 admin용, customer용 따로만들어야하는가?

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// 회원가입 API
	@PostMapping("/signup")
	public boolean signup(@RequestBody UserInfo userInfo) {
	    logger.info(userInfo.getFirstName());

	    // 이메일 중복 확인
	    if (repository.findByEmail(userInfo.getEmail()) != null) {
	        return false;  // 이미 존재하는 이메일인 경우 false 반환
	    }

	    // 비밀번호 암호화 후 저장
	    userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	    userInfo.setRole("USER");  // 기본 역할 설정
	    repository.save(userInfo);
	    return true;  // 성공적으로 등록되었을 경우 true 반환
	}

	
	
	
    /*@PostMapping("/signup")
    public String signup(@RequestBody UserInfo userInfo) {// string 대신에 boolean 으로 받을 수 있게
        
    	logger.info(userInfo.getFirstName());
    	
		// 이메일 중복 확인
        if (repository.findByEmail(userInfo.getEmail()) != null) {
            return "User with this email already exists.";
        }
        // 비밀번호 암호화 후 저장
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setRole("USER"); // 기본 역할 설정
        repository.save(userInfo);
        return "User registered successfully";
    }*/

    // 로그인 API
    @PostMapping("/login")
    public String login(@RequestBody UserInfo loginUser) {
        UserInfo user = repository.findByEmail(loginUser.getEmail());
        // 이메일 확인 및 비밀번호 일치 여부 확인
        if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return "login successful";
        }
        return "Invalid email or password";
    }
    
	
	
	//관리자용 사용자 조회용?
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

	
