package com.example.goshoes;

import java.io.InputStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.example.goshoes.model.ShoeDetailInfo;
import com.example.goshoes.model.ShoeDetailInfoRepository;
import com.example.goshoes.model.ShoeInfo;
import com.example.goshoes.model.ShoeInfoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class GoShoesApplication {

	@Autowired
    private ResourceLoader resourceLoader;
	
	public static void main(String[] args) {
		SpringApplication.run(GoShoesApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(ShoeInfoRepository shoeInfoRepository, ShoeDetailInfoRepository shoeDetailInfoRepository) {
		return args -> {
			
			Resource resource = resourceLoader.getResource("classpath:shoes_data.json");
			InputStream inputStream = resource.getInputStream();
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(inputStream);
			JsonNode shoesNode = jsonNode.get("shoes");
			
			for (JsonNode shoe : shoesNode) {
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
	            
	            ShoeInfo shoeInfo = new ShoeInfo(productCode, title, price, rating, reviewCount, color, style, brand, thumbnail);
	            shoeInfoRepository.save(shoeInfo);
	            shoeDetailInfoRepository.save(new ShoeDetailInfo(shoeInfo, images));
	            
	        }
		};
	}
}
