package com.example.goshoes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.ShoeDetailInfo;
import com.example.goshoes.model.ShoeDetailInfoRepository;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ShoeDetailInfoController {

  private static final Logger logger = LoggerFactory.getLogger(ShoeDetailInfoController.class);
	  
	@Autowired
    private ShoeDetailInfoRepository repository;

    @GetMapping("/shoes/{productCode}")
    public ResponseEntity<?> getShoeDetail(@PathVariable String productCode) {
    	
    	try {
    		
    		logger.info("ProductCode : "+ productCode);
    		ShoeDetailInfo info = repository.findByShoeInfo_productCode(productCode);
    		
			return new ResponseEntity<>(info, HttpStatus.OK);
			
		} catch (Exception e) {

		}
		return null;
    }
}
