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

import com.example.goshoes.model.ShoeInfo;
import com.example.goshoes.model.ShoeInfoRepository;
import com.example.goshoes.request.ShoeInfoRequest;

import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ShoeInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShoeInfoController.class);
	
	@Autowired
	ShoeInfoRepository repository;
	
	@GetMapping("/shoes")
	public ResponseEntity<?> getAllShoes() {

		try {
			List<ShoeInfo> shoesData = repository.findAll();
			logger.info("ProductCode : "+ shoesData.get(0).getProductCode());
			return new ResponseEntity<>(shoesData, HttpStatus.OK);
		
		} catch (Exception e) {
		}
		return null;

	}
	
	@GetMapping("/shoes/brand/{brandName}")
	public ResponseEntity<?> getShoesByBrand(@RequestBody ShoeInfoRequest shoesRequest) {

//		try {
//			Optional<ShoeInfo> shoeData = shoesRepository.getShoesByBrand(shoesRequest.getBrand());
//			if (shoeData.isPresent()) {
//				
//				
//				return new ResponseEntity<>(shoeData.get(), HttpStatus.OK);
//			}
//
//		} catch (Exception e) {
//
//		}
		return null;

	}

}
