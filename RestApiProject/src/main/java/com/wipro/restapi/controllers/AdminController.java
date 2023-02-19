package com.wipro.restapi.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wipro.restapi.entity.FoodItems;
import com.wipro.restapi.entity.OrderEntity;
import com.wipro.restapi.exception.FoodItemNotFoundException;
import com.wipro.restapi.exception.OrderNotFoundException;
import com.wipro.restapi.repository.FoodItemsRepository;
import com.wipro.restapi.repository.OrderRepository;



@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	FoodItemsRepository frepo;
	
	@Autowired
	OrderRepository orepo;
	
	@GetMapping("/home")
	public String home()
	{
		return "Welcome to admin Home Page";
	}
	
	//create new food items
	@PostMapping("/createitem")
	public ResponseEntity<Object> add(@Valid @RequestBody FoodItems fi)
	{	
		FoodItems fobj1=frepo.save(fi);
		URI loc=ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("admin/showitem/{id}").buildAndExpand(fobj1.getFoodId()).toUri();
		return ResponseEntity.created(loc).build();
	}
	
	//View Menu
	@GetMapping("/showallitems")
	public List<FoodItems> showall()
	{
		return frepo.findAll();
	}
	
	//View Orders
	@GetMapping("/vieworders")
	public List<OrderEntity> showorders()
	{
		List<OrderEntity> o1=orepo.findAll();
		if(o1.isEmpty())
			throw new OrderNotFoundException("No Orders Available");
		return o1;
	}
	
	//view particular item
	@GetMapping("/showitem/{id}")
	public Optional<FoodItems> show(@PathVariable("id") int foodid)
	
	{
		Optional<FoodItems> fobj=frepo.findById(foodid);
		if(!fobj.isPresent())
			throw new FoodItemNotFoundException("id: "+foodid);
		
		return frepo.findById(foodid);
	}
	
	//Update status of Particular Item
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@Valid @PathVariable("id") int foodid ,@RequestBody FoodItems fi)
	{	
		Optional<FoodItems> fobj=frepo.findById(foodid);
		if(!fobj.isPresent())
			throw new FoodItemNotFoundException("id: "+foodid);
		
		fi.setFoodId(foodid);
		FoodItems fobj1=frepo.save(fi);
		URI loc=ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("admin/showitem/{id}").buildAndExpand(fobj1.getFoodId()).toUri();
		return ResponseEntity.created(loc).build();
			
	}


}
