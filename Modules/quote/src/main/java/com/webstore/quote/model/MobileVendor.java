package com.webstore.quote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="MobileVendor")
public class MobileVendor {

	@Id
	@Column(name="id")
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	private float price;
	@ManyToOne
    @JoinColumn(name = "pro_id")
	private PhoneModels phoneModels;
	
	public PhoneModels getPhonemodels() {
		return phoneModels;
	}

	public void setPhonemodels(PhoneModels phoneModels) {
		this.phoneModels = phoneModels;
	}

	public MobileVendor(){
	}
	
	public MobileVendor(String name, String description, float price, PhoneModels phoneModels) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.phoneModels = phoneModels;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
