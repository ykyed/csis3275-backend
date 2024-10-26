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

	    // 이메일 중복 확인
	    if (repository.findByEmail(userInfo.getEmail()) != null) {
	        return new ResponseEntity<>(false, HttpStatus.CONFLICT); // 이메일 중복 시 false 응답
	    }

	    try {
	        // 비밀번호 암호화 후 저장
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	        userInfo.setRole("USER"); // 기본 역할 설정
	        repository.save(userInfo);
	        return new ResponseEntity<>(true, HttpStatus.OK); // 회원가입 성공 시 true 응답
	    } catch (Exception e) {
	        logger.error("Error occurred during signup", e);
	        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR); // 서버 에러 발생 시 false 응답
	    }
	}

	
//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody UserInfo loginUser) {
//		logger.info("login:" + loginUser);
//	    UserInfo user = repository.findByEmail(loginUser.getEmail());
//
//	    // 이메일이 존재하고 비밀번호가 일치할 경우 로그인 성공
//	    if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
//	    	user.setPassword(null);
//	        return new ResponseEntity<>(user, HttpStatus.OK);  
//	    }
//
//	    // 이메일 또는 비밀번호가 일치하지 않을 경우 로그인 실패
//	    return new ResponseEntity<>("failed to login.", HttpStatus.UNAUTHORIZED);  
//	}
	
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

	
