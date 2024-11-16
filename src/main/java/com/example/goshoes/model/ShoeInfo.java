package com.example.goshoes.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="shoeInfo")
public class ShoeInfo {
	
	private static final Logger logger = LoggerFactory.getLogger(ShoeInfo.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true, nullable = false)
	private String productCode;
	
	private String title;
	private double price;
	private double totalRating;
	private int reviewCount;
	private String color;
	private String style;
	private String brand;
	private String thumbnail;
	
	@ElementCollection
	@Column(length = 2000)
	private List<String> images;
	
	public ShoeInfo() {}
	
	public ShoeInfo(String productCode, String title, double price, double totalRating, int reviewCount, String color, String style, String brand, String thumbnail,  ArrayList<String> images) {
		this.productCode = productCode;
		this.title = title;
		this.price = price;
		this.totalRating = totalRating;
		this.reviewCount = reviewCount;
		this.color = color;
		this.style = style;
		this.brand = brand;
		this.thumbnail = thumbnail;
		this.images = images;
	}
	

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}	

	public double getRating() {
		if (totalRating == 0) {
			return 0;
		}
		double result = Double.parseDouble(String.format("%.1f", totalRating / reviewCount)); 
		logger.info("ShoeInfo, getrating: " + result);
		return result;
	}

	public void setRating(double rating) {
		this.totalRating += rating;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getBrand() {
		return this.brand;
	}
	
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}
	
	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

}
