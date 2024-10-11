package com.example.goshoes;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.goshoes.model.Shoes;
import com.example.goshoes.model.ShoesRepository;

@SpringBootApplication
public class GoShoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoShoesApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(ShoesRepository repository) {
		return args -> {
			repository.save(new Shoes("CW2288-111", "Nike Air Force 1 '07 White / White - White", 150.00, "NIKE", "https://cdn.shopify.com/s/files/1/0577/7784/8502/products/cw2288111_nike_air_force_1_07_white_white_1_232x.jpg?v=1632233947"));
			
			repository.save(new Shoes("JH9227", "adidas Handball Spezial JD Pink / Off White - Gum", 140.00, "ADIDAS", "//cdn.shopify.com/s/files/1/0577/7784/8502/files/JH9227_adidas_jd__handball_spezial_pink__off_white___gum_1_232x.jpg?v=1724234920"));
			
			int a = 0;
		};
	}
}
