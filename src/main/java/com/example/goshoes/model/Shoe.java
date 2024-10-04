package com.example.goshoes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="shoes")
public class Shoe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="shoeId")
	private String shoeId;
	
	@Column(name="brand")
	private String brand;
	
	
	public Shoe() {
		
	}
	
	public Shoe(String shoeId, String brand) {
		this.shoeId = shoeId;
		this.brand = brand;
	}
	
	public String getShoeId() {
		return shoeId;
	}
	
	public void setShoeId(String shoeId) {
		this.shoeId = shoeId;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
}
