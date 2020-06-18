
package com.webstore.customers.customer.model; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="customer_group_id")
	private int customerGroupId;

	@Column(name="email")
	private String email;

	@Column(name="created_at")
	private String createdAt;

	@Column(name="updated_at")
	private String updatedAt;

	@Column(name="is_acive")
	private int isAcive;

	public Customer() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(int customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getIsAcive() {
		return isAcive;
	}

	public void setIsAcive(int isAcive) {
		this.isAcive = isAcive;
	}

	@Override
	public String toString() {
		return "Customer [ id=" + id + ", customerGroupId=" + customerGroupId + ", email=" + email + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", isAcive=" + isAcive + "]";
	}
}