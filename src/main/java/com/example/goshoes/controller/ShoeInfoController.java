package com.example.goshoes.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/filter")
	public ResponseEntity<?> getFilterShoes(@RequestParam(required = false) List<String> brands, @RequestParam(required = false) List<String> styles, @RequestParam(required = false) List<String> colors) {

		try {
			if (brands != null && colors != null && styles != null) {
				List<ShoeInfo> shoeData = repository.findByBrandInAndStyleInAndColorIn(brands, styles, colors);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        } 
			else if (brands != null && colors != null) {
	        	List<ShoeInfo> shoeData = repository.findByBrandInAndColorIn(brands, colors);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (brands != null && styles != null) {
	        	List<ShoeInfo> shoeData = repository.findByBrandInAndStyleIn(brands, styles);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (colors != null && styles != null) {
	        	List<ShoeInfo> shoeData = repository.findByStyleInAndColorIn(styles, colors);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (brands != null) {
	        	logger.info(brands.get(0));
	        	List<ShoeInfo> shoeData = repository.findByBrandIn(brands);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (styles != null) {
	        	List<ShoeInfo> shoeData = repository.findByStyleIn(styles);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (colors != null) {
	        	List<ShoeInfo> shoeData = repository.findByColorIn(colors);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else {
	        	return getAllShoes();
	        }

		} catch (Exception e) {
		}
		return null;
	}
	
	@GetMapping("/brands")
    public ResponseEntity<?> getAllBrands() {
        List<String> brands = repository.findDistinctBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }
	
	@GetMapping("/styles")
    public ResponseEntity<?> getAllStyles() {
        List<String> styles = repository.findDistinctStyles();
        return new ResponseEntity<>(styles, HttpStatus.OK);
    }
	
	@GetMapping("/colors")
    public ResponseEntity<?> getAllColors() {
        List<String> colors = repository.findDistinctColors();
        return new ResponseEntity<>(colors, HttpStatus.OK);
    }

}
