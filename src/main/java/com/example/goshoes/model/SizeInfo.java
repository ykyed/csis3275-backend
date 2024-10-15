package com.example.goshoes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SizeInfo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productCode", referencedColumnName = "productCode")
    private ShoeInfo shoeInfo;

    private double size;
    
	private int quantity;
	
	public SizeInfo() {}
	
	public SizeInfo(ShoeInfo shoeInfo, double size, int quantity) {
		this.shoeInfo = shoeInfo;
		this.size = size;
		this.quantity = quantity;
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

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
