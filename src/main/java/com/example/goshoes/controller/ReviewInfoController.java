package com.example.goshoes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.event.AddReviewEvent;
import com.example.goshoes.model.ReviewInfo;
import com.example.goshoes.model.ReviewInfoRepository;

import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ReviewInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReviewInfoController.class);
	
	 @Autowired 
	 private ReviewInfoRepository repository;
	 
	 private final ApplicationEventPublisher eventPublisher; 
	 
	 public ReviewInfoController(ApplicationEventPublisher eventPublisher) {
		 this.eventPublisher = eventPublisher;
	 }
	  
	 @GetMapping("/reviews/{productCode}") 
	 public ResponseEntity<?> getReviewsByProductCode(@PathVariable String productCode) {
	 
		 try {
			 logger.info("ProductCode : "+ productCode);
	 
			 List<ReviewInfo> info = repository.findByProductCode(productCode);
			 
			 logger.info("Review Size: "+ info.size());
	 
			 return new ResponseEntity<>(info, HttpStatus.OK);
	 
		 } 
		 catch (Exception e) {
			 logger.info("Exception: "+ e.getMessage());
			 return new ResponseEntity<>("Failed to getReviewsByProductCode.", HttpStatus.INTERNAL_SERVER_ERROR);
	 
		 }
	 }
	 
	 @PostMapping("/reviews")
	 public ResponseEntity<?> addReview(@RequestBody ReviewInfo reviewInfo) {
	 	
		 try {
			 logger.info("addReview : "+ reviewInfo.getRating());
			 eventPublisher.publishEvent(new AddReviewEvent(reviewInfo.getProductCode(), reviewInfo.getRating()));
			 repository.save(reviewInfo);
			 return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
		} 
		catch (Exception e) {
			return new ResponseEntity<>("Failed to add review.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 }
}
