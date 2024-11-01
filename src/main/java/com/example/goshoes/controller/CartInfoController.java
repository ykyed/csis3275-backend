package com.example.goshoes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.goshoes.model.CartInfo;
import com.example.goshoes.model.CartInfoRepository;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CartInfoController {

	private static final Logger logger = LoggerFactory.getLogger(CartInfoController.class);
	
	 @Autowired 
	 private CartInfoRepository repository;
	 
	 @GetMapping("/cart/{userEmail}") 
	 public ResponseEntity<?> getCartItemsByUserEmail(@PathVariable String userEmail) {
	 
		 try {
			 logger.info("userEmail : "+ userEmail);
	 
			 List<CartInfo> info = repository.findByUserEmail(userEmail);
			 
			 logger.info("userEmail: "+ info.size());
	 
			 return new ResponseEntity<>(info, HttpStatus.OK);
	 
		 } 
		 catch (Exception e) {
			 logger.info("Exception: "+ e.getMessage());
			 return new ResponseEntity<>("Failed to getCartItemsByUserEmail.", HttpStatus.INTERNAL_SERVER_ERROR);
	 
		 }
	 }
	 
	 @PostMapping("/cart")
	 public ResponseEntity<?> addItem(@RequestBody CartInfo cartInfo) {
		 
		 try {
			 
			 List<CartInfo> infos = repository.findByUserEmail(cartInfo.getUserEmail());
			 for (CartInfo info : infos) {
				if (info.getProductCode().equals(cartInfo.getProductCode()) == true
						&& info.getSize() == cartInfo.getSize()) {
					// already exist item in db
					info.setQuantity(info.getQuantity() + cartInfo.getQuantity());
					repository.save(info);
					return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
				}
			 }
			 
			 repository.save(cartInfo);	 
			 return new ResponseEntity<>("Item added successfully", HttpStatus.OK);
	 
		 } 
		 catch (Exception e) {
			 logger.info("Exception: "+ e.getMessage());
			 return new ResponseEntity<>("Failed to add item to cart.", HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	 }
	 
	 
	 @PutMapping("/cart")
	 public ResponseEntity<?> updateItem(@RequestBody CartInfo cartInfo) {
		 
		 try {
			 repository.save(cartInfo);	 
			 return new ResponseEntity<>("Item updated successfully", HttpStatus.OK);
	 
		 } 
		 catch (Exception e) {
			 logger.info("Exception: "+ e.getMessage());
			 return new ResponseEntity<>("Failed to update item to cart.", HttpStatus.INTERNAL_SERVER_ERROR);
	 
		 }
	 }
	 
	 @Transactional
	 @DeleteMapping("/cart/user/{userEmail}")
	 public ResponseEntity<?> deleteItems(@PathVariable String userEmail) {
		 
		 try {
			 repository.deleteByUserEmail(userEmail);
			 return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
	 
		 } 
		 catch (Exception e) {
			 logger.info("Exception: "+ e.getMessage());
			 return new ResponseEntity<>("Failed to delete item from cart.", HttpStatus.INTERNAL_SERVER_ERROR);
	 
		 }
	 }
	 
	 @DeleteMapping("/cart/{id}")
	 public ResponseEntity<?> deleteItem(@PathVariable long id) {
		 
		 try {
			 repository.deleteById(id);
			 return new ResponseEntity<>("Item deleted successfully", HttpStatus.OK);
	 
		 } 
		 catch (Exception e) {
			 logger.info("Exception: "+ e.getMessage());
			 return new ResponseEntity<>("Failed to delete item from cart.", HttpStatus.INTERNAL_SERVER_ERROR);
	 
		 }
	 }
	 
}
