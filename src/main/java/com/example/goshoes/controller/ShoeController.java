package com.example.goshoes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.Shoe;
import com.example.goshoes.model.ShoeRepository;
import com.example.goshoes.request.ShoeRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ShoeController {
	
	@Autowired
	ShoeRepository shoeRepository;
	
	@PostMapping("/shoelist")
	public ResponseEntity<?> shoeList(@RequestBody ShoeRequest shoeRequest) {

		try {
			Optional<Shoe> shoeData = shoeRepository.findByBrand(shoeRequest.getBrand());
			if (shoeData.isPresent()) {
				
				return new ResponseEntity<>(shoeData.get(), HttpStatus.OK);
			}

		} catch (Exception e) {

		}
		return null;

	}

}
