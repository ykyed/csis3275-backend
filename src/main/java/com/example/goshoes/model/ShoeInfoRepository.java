package com.example.goshoes.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoeInfoRepository extends JpaRepository<ShoeInfo, Long> {

	List<ShoeInfo> findAll();
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.brand IN :brands AND s.style IN :styles AND s.color IN :colors AND sz.size IN :sizes AND sz.quantity > 1")
	List<ShoeInfo> findByBrandAndStyleAndColorAndSizeWithQualtity(List<String> brands, List<String> styles, List<String> colors, List<Double> sizes);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.brand IN :brands AND s.style IN :styles AND s.color IN :colors")
	List<ShoeInfo> findByBrandAndStyleAndColor(List<String> brands, List<String> styles, List<String> colors);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.brand IN :brands AND s.style IN :styles AND sz.size IN :sizes AND sz.quantity > 1")
	List<ShoeInfo> findByBrandAndStyleAndSizeWithQualtity(List<String> brands, List<String> styles, List<Double> sizes);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.brand IN :brands AND s.color IN :colors AND sz.size IN :sizes AND sz.quantity > 1")
	List<ShoeInfo> findByBrandAndColorAndSizeWithQualtity(List<String> brands, List<String> colors, List<Double> sizes);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.style IN :styles AND s.color IN :colors AND sz.size IN :sizes AND sz.quantity > 1")
	List<ShoeInfo> findByStyleInAndColorInAndSizeWithQualtity(List<String> styles, List<String> colors, List<Double> sizes);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.brand IN :brands AND s.style IN :styles")
	List<ShoeInfo> findByBrandAndStyle(List<String> brands, List<String> styles);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.brand IN :brands AND s.color IN :colors")
	List<ShoeInfo> findByBrandAndColor(List<String> brands, List<String> colors);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.brand IN :brands AND sz.size IN :sizes AND sz.quantity > 1")
	List<ShoeInfo> findByBrandAndSizeWithQualtity(List<String> brands, List<Double> sizes);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.style IN :styles AND s.color IN :colors")
	List<ShoeInfo> findByStyleAndColor(List<String> styles, List<String> colors);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.style IN :styles AND sz.size IN :sizes AND sz.quantity > 1")
	List<ShoeInfo> findByStyleAndSizeWithQualtity(List<String> styles, List<Double> sizes);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes sz WHERE s.color IN :colors AND sz.size IN :sizes AND sz.quantity > 1")
	List<ShoeInfo> findByColorAndSizeWithQualtity(List<String> colors, List<Double> sizes);
	
	List<ShoeInfo> findByBrandIn(List<String> brands);
	List<ShoeInfo> findByStyleIn(List<String> styles);
	List<ShoeInfo> findByColorIn(List<String> colors);
	
	@Query("SELECT s FROM ShoeInfo s JOIN s.sizes si WHERE si.size IN :sizes AND si.quantity > 0")
    List<ShoeInfo> findBySizeAndQuantity(@Param("sizes") List<Double> sizes);
	
	@Query("SELECT DISTINCT s.brand FROM ShoeInfo s")
	List<String> findDistinctBrands();
	
	@Query("SELECT DISTINCT s.style FROM ShoeInfo s")
	List<String> findDistinctStyles();
	
	@Query("SELECT DISTINCT s.color FROM ShoeInfo s")
	List<String> findDistinctColors();
}

