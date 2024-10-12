package com.example.goshoes.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeInfoRepository extends JpaRepository<ShoeInfo, Long> {

	List<ShoeInfo> findAll();
	
	Optional<ShoeInfo> getShoesByBrand(String brand);
}
