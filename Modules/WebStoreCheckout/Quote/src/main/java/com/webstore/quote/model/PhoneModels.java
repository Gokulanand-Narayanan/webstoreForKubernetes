package com.webstore.quote.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PhoneModels")
public class PhoneModels {

	@Id
	@Column(name="id")
	@GeneratedValue
	private int id;
	private String name;
	@OneToMany(fetch=FetchType.LAZY, mappedBy = "phoneModels", cascade = CascadeType.ALL)
	private Set<MobileVendor> mobileVendor;
	
	public Set<MobileVendor> getMobileVendor() {
		return mobileVendor;
	}

	public void setMobileVendor(Set<MobileVendor> mobileVendor) {
		this.mobileVendor = mobileVendor;
	}

	public PhoneModels(int id,String name,Set<MobileVendor> mobileVendor) {
		this.id=id;
		this.name=name;
		this.mobileVendor=mobileVendor;
		
	}
	
	public PhoneModels() {
	}
	
	public PhoneModels(String name) {
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
	
	
	
}
