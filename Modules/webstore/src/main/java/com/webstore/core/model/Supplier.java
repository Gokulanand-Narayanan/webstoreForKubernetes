package com.webstore.core.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="supplier")
public class Supplier {

	@Id
	@Column(name="id")
	@GeneratedValue
	private int id;
	private String name;
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "supplier", cascade = CascadeType.ALL)
	private Set<Product> products;
	
	public Supplier() {
	}
	
	public Supplier(String name) {
		this.name = name;
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
	
	//@ElementCollection(targetClass=Product.class)
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
}
