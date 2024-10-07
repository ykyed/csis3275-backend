package com.example.goshoes.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoesRepository extends JpaRepository<Shoes, Long> {

	List<Shoes> findAll();
	
	Optional<Shoes> getShoesByBrand(String brand);
}
