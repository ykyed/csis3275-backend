package com.example.goshoes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.SizeInfoRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SizeInfoController {
	
private static final Logger logger = LoggerFactory.getLogger(SizeInfoController.class);
	
	@Autowired
	SizeInfoRepository repository;
	
	@GetMapping("/sizes")
    public ResponseEntity<?> getAllColors() {
        List<Double> sizes = repository.findDistinctSizes();
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }
}
