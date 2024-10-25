package com.example.goshoes.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewInfoRepository extends JpaRepository<ReviewInfo, Long> {

	List<ReviewInfo> findByProductCode(String productCode);
	
	void deleteByProductCode(String productCode);
}
