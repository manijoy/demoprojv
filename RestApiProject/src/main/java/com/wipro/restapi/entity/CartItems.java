package com.wipro.restapi.entity;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity //class can be mapped to a DB
@Data
@NoArgsConstructor // no argument constructor for a class
@AllArgsConstructor // constructor for every field
@Getter
@Setter
@SequenceGenerator(name="s1",initialValue=100,allocationSize=1)
public class CartItems{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="s1")
	private int cartid;
	
	@ManyToMany
	private List<FoodItems> listofitems;
	
	@Min(value=0,message="Total Price should be positive number")
	private double totalprice;
	
	@OneToOne(cascade=CascadeType.ALL,mappedBy="cart")
	@JsonBackReference
	private OrderEntity oe;

	public int getCartid() {
		return cartid;
	}

	public void setCartid(int cartid) {
		this.cartid = cartid;
	}

	public List<FoodItems> getListofitems() {
		return listofitems;
	}

	public void setListofitems(List<FoodItems> listofitems) {
		this.listofitems = listofitems;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	public OrderEntity getOe() {
		return oe;
	}

	public void setOe(OrderEntity oe) {
		this.oe = oe;
	}

	
	
}
