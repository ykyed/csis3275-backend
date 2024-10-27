package com.example.goshoes.event;

public class AddReviewEvent {
	
	private final String productCode;
	private final double rating;
	
	public AddReviewEvent(String productCode, double rating) {
		this.productCode = productCode;
		this.rating = rating;
	}
	
	public String getProductCode() {
		return this.productCode;
	}
	
	public double getRating() {
		return this.rating;
	}
}
