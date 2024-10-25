package com.example.goshoes.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ShoeInfoRepository extends JpaRepository<ShoeInfo, Long>, JpaSpecificationExecutor<ShoeInfo>  {

	List<ShoeInfo> findAll();
	
	ShoeInfo findByProductCode(String productCode);
	
	boolean existsByProductCode(String productCode);
	
	void deleteByProductCode(String productCode);
	
	@Query("SELECT DISTINCT s.brand FROM ShoeInfo s")
	List<String> findDistinctBrands();
	
	@Query("SELECT DISTINCT s.style FROM ShoeInfo s")
	List<String> findDistinctStyles();
	
	@Query("SELECT DISTINCT s.color FROM ShoeInfo s")
	List<String> findDistinctColors();
}

