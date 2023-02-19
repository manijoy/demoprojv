package com.wipro.restapi.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SequenceGenerator(name="s2",initialValue=500,allocationSize=1)
public class OrderEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="s2")
	private int orderId;
	
	@NotBlank
	@Size(min=10,max=10,message="Number should be 10 digits only")
	private String phno;
	
	//@NotBlank
	private String email;
	
	private String orderStatus;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JsonManagedReference
	private CartItems cart;
	
	@JsonIgnore
	@ManyToOne
	private User user;
	
	@Min(value=0,message="Total Price should be positive number")
	private double totalPlusGST;

	private Date orderDate;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public CartItems getCart() {
		return cart;
	}

	public void setCart(CartItems cart) {
		this.cart = cart;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getTotalPlusGST() {
		return totalPlusGST;
	}

	public void setTotalPlusGST(double totalPlusGST) {
		this.totalPlusGST = totalPlusGST;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	
	
}
