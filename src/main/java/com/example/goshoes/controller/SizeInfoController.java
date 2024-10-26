package com.example.goshoes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.ShoeInfo;
import com.example.goshoes.model.SizeInfo;
import com.example.goshoes.model.SizeInfoRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SizeInfoController {
	
private static final Logger logger = LoggerFactory.getLogger(SizeInfoController.class);
	
	@Autowired
	SizeInfoRepository repository;
	
	@GetMapping("/sizes")
    public ResponseEntity<?> getAllSizes() {
        List<Double> sizes = repository.findDistinctSizes();
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }
	
	@GetMapping("/sizes/{productCode}")
    public ResponseEntity<?> getSizesByProductCode(@PathVariable String productCode) {
		logger.info("getSizesByProductCode: " + productCode);
        List<SizeInfo> info = repository.findByProductCode(productCode);
        logger.info("getSizesByProductCode2: " + info.get(0).getProductCode() + ", " + info.get(0).getQuantity() + ", " + info.size());
        return new ResponseEntity<>(info, HttpStatus.OK);
    }
	
	
	@PutMapping("admin/sizes/{productCode}")
	public ResponseEntity<?> updateSizes(@PathVariable String productCode, @RequestBody List<SizeInfo> infos) {
		 
		logger.info("updateSizes sizeInfo: "+ infos.get(0).getProductCode());
		
		List<SizeInfo> existingSizes = repository.findByProductCode(productCode);
		
		if (existingSizes != null) {
			
			for (int i = 0; i < infos.size(); i++) {
				
				SizeInfo info = infos.get(i);
				
				info.setId(existingSizes.get(i).getId());
				info.setProductCode(existingSizes.get(i).getProductCode());
				repository.save(info);
			}
			return new ResponseEntity<>("Size update successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Shoe not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/admin/sizes/{productCode}")
	public ResponseEntity<?> addSizes(@PathVariable String productCode, @RequestBody List<SizeInfo> sizeInfos) {
		logger.info("addSizes sizeInfo: "+ sizeInfos.size());
		try {
				for (int i = 0; i < sizeInfos.size(); i++) {
				
					SizeInfo info = sizeInfos.get(i);
					info.setProductCode(productCode);
					repository.save(info);
				}
			return new ResponseEntity<>("Size added successfully", HttpStatus.CREATED);
		}
		catch(Exception e) {
			logger.info("Exception: "+ e.getMessage());
			return new ResponseEntity<>("Failed to addSizes.", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
}
