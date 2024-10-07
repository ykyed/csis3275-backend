package com.example.goshoes.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="shoes")
public class Shoes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="productCode")
	private String productCode;
	
	
	@Column(name="title")
	private String title;
	
	@Column(name="price")
	private double price;
	
	@Column(name="brand")
	private String brand;
	
	@Column(name="thumbnail")
	private String thumbnail;
	
	public Shoes() {
		
	}
	
	public Shoes(String productCode, String title, double price, String brand, String thumbnail) {
		this.productCode = productCode;
		this.title = title;
		this.price = price;
		this.brand = brand;
		this.thumbnail = thumbnail;
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
	
	public void setTile(String title) {
		this.title = title;
	}
	
	public String getTile() {
		return title;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
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
	
	
}
