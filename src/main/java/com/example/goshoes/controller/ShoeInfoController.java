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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.ShoeInfo;
import com.example.goshoes.model.ShoeInfoRepository;


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
		
		} 
		catch (Exception e) {
		}
		return null;

	}
	
	@GetMapping("/filter")
	public ResponseEntity<?> getFilterShoes(@RequestParam(required = false) List<String> brands, @RequestParam(required = false) List<String> styles, @RequestParam(required = false) List<String> colors, @RequestParam(required = false) List<Double> sizes) {

		try {
			if (brands != null && colors != null && styles != null && sizes != null) {
				List<ShoeInfo> shoeData = repository.findByBrandAndStyleAndColorAndSizeWithQualtity(brands, styles, colors, sizes);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        } 
			
			else if (brands != null && colors != null && styles != null ) {
				List<ShoeInfo> shoeData = repository.findByBrandAndStyleAndColor(brands, styles, colors);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
			else if (brands != null && styles != null && sizes != null ) {
				List<ShoeInfo> shoeData = repository.findByBrandAndStyleAndSizeWithQualtity(brands, styles, sizes);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        } 
			else if (brands != null && colors != null && sizes != null ) {
				List<ShoeInfo> shoeData = repository.findByBrandAndColorAndSizeWithQualtity(brands, colors, sizes);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
			else if (styles != null && colors != null && sizes != null ) {
				List<ShoeInfo> shoeData = repository.findByStyleInAndColorInAndSizeWithQualtity(styles, colors, sizes);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
			
			else if (brands != null && colors != null) {
	        	List<ShoeInfo> shoeData = repository.findByBrandAndColor(brands, colors);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (brands != null && styles != null) {
	        	List<ShoeInfo> shoeData = repository.findByBrandAndStyle(brands, styles);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (brands != null && sizes != null) {
	        	List<ShoeInfo> shoeData = repository.findByBrandAndSizeWithQualtity(brands, sizes);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (styles != null && colors != null) {
	        	List<ShoeInfo> shoeData = repository.findByStyleAndColor(styles, colors);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (styles != null && sizes != null) {
	        	List<ShoeInfo> shoeData = repository.findByStyleAndSizeWithQualtity(styles, sizes);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (colors != null && sizes != null) {
	        	List<ShoeInfo> shoeData = repository.findByColorAndSizeWithQualtity(styles, sizes);	
				return new ResponseEntity<>(shoeData, HttpStatus.OK);
	        }
	        else if (brands != null) {
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
	        else if (sizes != null) {
	        	List<ShoeInfo> shoeData = repository.findBySizeAndQuantity(sizes);
	        	logger.info("size + " + shoeData.size());
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
