package com.example.goshoes.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SizeInfoRepository extends JpaRepository<SizeInfo, Long> {

	@Query("SELECT DISTINCT s.size FROM SizeInfo s")
	List<Double> findDistinctSizes();
}
