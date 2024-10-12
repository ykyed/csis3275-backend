package com.example.goshoes.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeDetailInfoRepository extends JpaRepository<ShoeDetailInfo, Long> {

	ShoeDetailInfo findByShoeInfo_productCode(String productCode);
	
}
