package com.example.goshoes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.ReviewInfoRepository;
import com.example.goshoes.model.ShoeInfo;
import com.example.goshoes.model.ShoeInfoRepository;
import com.example.goshoes.model.ShoeInfoSpecifications;

import jakarta.transaction.Transactional;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ShoeInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ShoeInfoController.class);
	
	@Autowired
	ShoeInfoRepository repository;
	
	@Autowired
	ReviewInfoRepository reviewRepository;
	
	@GetMapping("/shoes")
	public ResponseEntity<?> getAllShoes() {

		try {
			List<ShoeInfo> shoesData = repository.findAll();
			return new ResponseEntity<>(shoesData, HttpStatus.OK);
		
		} 
		catch (Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to getAllShoes.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/filter")
	public ResponseEntity<?> getFilterShoes(@RequestParam(required = false) List<String> brands, @RequestParam(required = false) List<String> styles, @RequestParam(required = false) List<String> colors, @RequestParam(required = false) List<Double> sizes) {
		try {
			
	        Specification<ShoeInfo> spec = Specification.where(null);

	        if (brands != null && !brands.isEmpty()) {
	            spec = spec.and(ShoeInfoSpecifications.hasBrand(brands));
	        }
	        if (styles != null && !styles.isEmpty()) {
	            spec = spec.and(ShoeInfoSpecifications.hasStyle(styles));
	        }
	        if (colors != null && !colors.isEmpty()) {
	            spec = spec.and(ShoeInfoSpecifications.hasColor(colors));
	        }
	        if (sizes != null && !sizes.isEmpty()) {
	            spec = spec.and(ShoeInfoSpecifications.hasSizeAndQuantity(sizes));
	        }
	        List<ShoeInfo> info = repository.findAll(spec);
	        
			return new ResponseEntity<>(info, HttpStatus.OK);
		
		} 
		catch (Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to getFilterShoes.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/brands")
    public ResponseEntity<?> getAllBrands() {
		try {
			List<String> brands = repository.findDistinctBrands();
	        return new ResponseEntity<>(brands, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to getAllBrands.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }
	
	@GetMapping("/styles")
    public ResponseEntity<?> getAllStyles() {
		try {
			List<String> styles = repository.findDistinctStyles();
	        return new ResponseEntity<>(styles, HttpStatus.OK);
		}
        catch (Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to getAllStyles.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@GetMapping("/colors")
    public ResponseEntity<?> getAllColors() {
		try {
			List<String> colors = repository.findDistinctColors();
	        return new ResponseEntity<>(colors, HttpStatus.OK);
		}
        catch (Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to getAllColors.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@PostMapping("/shoes")
	public ResponseEntity<?> addShoe(@RequestBody ShoeInfo shoeInfo) {
		try {
			repository.save(shoeInfo);
			return new ResponseEntity<>("Shoe added successfully", HttpStatus.CREATED);
		}
		catch(Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to addShoe.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/shoes")
	public ResponseEntity<?> updateShoe(@RequestBody ShoeInfo shoeInfo) {
		try {
			String productCode = shoeInfo.getProductCode();
			
			ShoeInfo existingShoe = repository.findByProductCode(productCode); 
			if (existingShoe != null) {
				shoeInfo.setId(existingShoe.getId());
				repository.save(shoeInfo);
				return new ResponseEntity<>("Shoe update successfully", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Shoe not found", HttpStatus.NOT_FOUND);
			}
			
		}
		catch(Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to updateShoe.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	@DeleteMapping("/shoes/{productCode}")
	public ResponseEntity<?> deleteShoe(@PathVariable String productCode) {
		
		try {
			if (repository.existsByProductCode(productCode)) {
				
				reviewRepository.deleteByProductCode(productCode);
				repository.deleteByProductCode(productCode);
				return new ResponseEntity<>("Shoe delete successfully", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Shoe not found", HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to deleteShoe.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
