package com.example.goshoes.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shoeDetailInfo")
public class ShoeDetailInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "productCode", referencedColumnName = "productCode")
	private ShoeInfo shoeInfo;
	
	@ElementCollection
	private List<String> images;
	
	public ShoeDetailInfo() {}
	
	public ShoeDetailInfo(ShoeInfo info, ArrayList<String> images) {
		this.shoeInfo = info;
		this.images = images;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ShoeInfo getShoeInfo() {
		return shoeInfo;
	}

	public void setShoeInfo(ShoeInfo shoeInfo) {
		this.shoeInfo = shoeInfo;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
}
