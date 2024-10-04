package com.example.goshoes.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoeRepository extends JpaRepository<Shoe, Long> {

	Optional<Shoe> findByBrand(String brand);
}
