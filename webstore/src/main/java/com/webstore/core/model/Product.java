package com.webstore.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {

	@Id
	@Column(name="id")
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	private float price;
	@ManyToOne
    @JoinColumn(name = "supplierid")
	private Supplier supplier;
	
	public Product(){
	}
	
	public Product(String name, String description, float price, Supplier supplier) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.supplier = supplier;
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

	public Supplier getSupplier() {
		return supplier;
	}

	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	
	
}
