package com.example.goshoes.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoeInfoRepository extends JpaRepository<ShoeInfo, Long> {

	List<ShoeInfo> findAll();
	
	List<ShoeInfo> findByBrandInAndStyleInAndColorIn(List<String> brands, List<String> styles, List<String> colors);
	
	List<ShoeInfo> findByBrandInAndStyleIn(List<String> brands, List<String> styles);
	List<ShoeInfo> findByBrandInAndColorIn(List<String> brands, List<String> colors);
	List<ShoeInfo> findByStyleInAndColorIn(List<String> styles, List<String> colors);
	
	List<ShoeInfo> findByBrandIn(List<String> brands);
	List<ShoeInfo> findByStyleIn(List<String> styles);
	List<ShoeInfo> findByColorIn(List<String> colors);
	
	@Query("SELECT DISTINCT s.brand FROM ShoeInfo s")
	List<String> findDistinctBrands();
	
	@Query("SELECT DISTINCT s.style FROM ShoeInfo s")
	List<String> findDistinctStyles();
	
	@Query("SELECT DISTINCT s.color FROM ShoeInfo s")
	List<String> findDistinctColors();
}
