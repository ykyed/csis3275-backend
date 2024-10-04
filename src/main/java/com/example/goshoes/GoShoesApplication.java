package com.example.goshoes;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.goshoes.model.Shoe;
import com.example.goshoes.model.ShoeRepository;

@SpringBootApplication
public class GoShoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoShoesApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(ShoeRepository repository) {
		return args -> {
			repository.save(new Shoe("QWER-123", "Nike"));
		};
	}


}
