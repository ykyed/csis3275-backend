package com.example.goshoes;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.goshoes.model.ReviewInfo;
import com.example.goshoes.model.ReviewInfoRepository;
import com.example.goshoes.model.ShoeInfo;
import com.example.goshoes.model.ShoeInfoRepository;
import com.example.goshoes.model.SizeInfo;
import com.example.goshoes.model.SizeInfoRepository;
import com.example.goshoes.model.UserInfo;
import com.example.goshoes.model.UserInfoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class GoShoesApplication {

	private static final Logger logger = LoggerFactory.getLogger(GoShoesApplication.class);
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(GoShoesApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(ShoeInfoRepository shoeInfoRepository, UserInfoRepository userRepository, SizeInfoRepository sizeRepository, ReviewInfoRepository reviewRepository) {
		return args -> {
			
			// add shoe info to DB
			Resource resource = resourceLoader.getResource("classpath:shoes_data.json");
			InputStream inputStream = resource.getInputStream();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(inputStream);
			JsonNode node = jsonNode.get("shoes");
			
			for (JsonNode shoe : node) {
	            String productCode = shoe.get("productCode").asText();
	            String title = shoe.get("title").asText();
	            Double price = shoe.get("price").asDouble();
	            Double rating = shoe.get("rating").asDouble();
	            int reviewCount = shoe.get("reviewCount").asInt();
	            String color = shoe.get("color").asText();
	            String style = shoe.get("style").asText();
	            String brand = shoe.get("brand").asText();
	            String thumbnail = shoe.get("thumbnail").asText();
	            JsonNode imageNode = shoe.get("images");
	            ArrayList<String> images = objectMapper.convertValue(imageNode, new TypeReference<ArrayList<String>>(){});
	            
	            ShoeInfo shoeInfo = new ShoeInfo(productCode, title, price, rating, reviewCount, color, style, brand, thumbnail, images);
	            shoeInfoRepository.save(shoeInfo);
	            
	            // size
	            double[] sizeArr = {3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13 };
	            Random rand = new Random();
	            
	            for (int i = 0; i < sizeArr.length; i++) {
	            	sizeRepository.save(new SizeInfo(productCode, sizeArr[i], rand.nextInt(10)));
	            }
	        }
			
			// add review info to DB
			resource = resourceLoader.getResource("classpath:review_data.json");
			inputStream = resource.getInputStream();
			jsonNode = objectMapper.readTree(inputStream);
			node = jsonNode.get("review");
			
			for (JsonNode shoe : node) {
	            String productCode = shoe.get("productCode").asText();
	            String title = shoe.get("title").asText();
	            String comment = shoe.get("comment").asText();
	            Double rating = shoe.get("rating").asDouble();
	            
	            ReviewInfo reviewInfo = new ReviewInfo(productCode, title, comment, rating);
	            reviewRepository.save(reviewInfo);
	        }
			
			// add user to DB
			userRepository.save(new UserInfo("admin@goshoes.com", passwordEncoder.encode("Admin123"), "ADMIN","admin","admin","2024-11-27"));
			userRepository.save(new UserInfo("wooastudio1012@gmail.com", passwordEncoder.encode("Qwer1234"), "USER","Dan","Do","2024-11-28"));			
		};
	}
}
