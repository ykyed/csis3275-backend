package com.example.goshoes.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartInfoRepository extends JpaRepository<CartInfo, Long> {
	
	List<CartInfo> findByUserEmail(String userEmail);
	
	void deleteAllByUserEmail(String userEmail);
}
