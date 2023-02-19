package com.wipro.restapi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
//@JsonFilter("showmenu")
public class FoodItems {
	
	@Id
	private int foodId;
	
	@NotBlank
	private String foodName;
	
	@Min(value=1,message="FoodPrice should be positive number")
	@Max(value=1000,message="FoodPrice should be less than 1000")
	private double foodPrice;

	
	@NotBlank
	private String status;


	public int getFoodId() {
		return foodId;
	}


	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}


	public String getFoodName() {
		return foodName;
	}


	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}


	public double getFoodPrice() {
		return foodPrice;
	}


	public void setFoodPrice(double foodPrice) {
		this.foodPrice = foodPrice;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
