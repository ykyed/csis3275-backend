package com.example.goshoes.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>  {

	List<UserInfo> findAll();
	
	UserInfo findByEmail(String email);
}