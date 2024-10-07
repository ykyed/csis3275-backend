package com.example.goshoes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.Shoes;
import com.example.goshoes.model.ShoesRepository;
import com.example.goshoes.request.ShoesRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ShoesController {
	
	@Autowired
	ShoesRepository shoesRepository;
	
	@PostMapping("/shoes")
	public ResponseEntity<?> findAllShoes(@RequestBody ShoesRequest shoesRequest) {

		try {
			List<Shoes> shoesData = shoesRepository.findAll();
			
			return new ResponseEntity<>(shoesData, HttpStatus.OK);
		
		} catch (Exception e) {

		}
		return null;

	}
	
	@GetMapping("/shoes/brand/{brandName}")
	public ResponseEntity<?> getShoesByBrand(@RequestBody ShoesRequest shoesRequest) {

		try {
			Optional<Shoes> shoeData = shoesRepository.getShoesByBrand(shoesRequest.getBrand());
			if (shoeData.isPresent()) {
				
				return new ResponseEntity<>(shoeData.get(), HttpStatus.OK);
			}

		} catch (Exception e) {

		}
		return null;

	}

}
