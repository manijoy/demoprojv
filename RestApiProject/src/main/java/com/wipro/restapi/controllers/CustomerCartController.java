package com.wipro.restapi.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.restapi.entity.CartItems;
import com.wipro.restapi.entity.FoodItems;
import com.wipro.restapi.exception.CartNotFoundException;
import com.wipro.restapi.exception.FoodItemNotFoundException;
import com.wipro.restapi.repository.CartItemsRepository;
import com.wipro.restapi.repository.FoodItemsRepository;
import com.wipro.restapi.repository.UserRepository;
import com.wipro.restapi.service.UserDetailsServiceImpl;
import com.wipro.restapi.util.JwtUtility;

@RestController
@RequestMapping("/customer")
public class CustomerCartController {

	@Autowired 
	CartItemsRepository crepo;
	
	@Autowired
	FoodItemsRepository frepo;
	
	@Autowired
	JwtUtility jutil;
	
	@Autowired
	UserRepository urepo;
		
	@Autowired
	UserDetailsServiceImpl uservice;
	
	@GetMapping("/home")
	public String home()
	{	
		return "Welcome to Customer Cart Home Page";
	}
	
	//view menu
	@GetMapping("/showmenu")
	public List<FoodItems> show()
	{	
		List<FoodItems> l1 =frepo.findByStatusLike("Available");
		if(l1.isEmpty())
			throw new FoodItemNotFoundException("No Food Available");
		
		return l1;
	}
	
	//Adding to Cart
	@PostMapping("/addtocart")
	public EntityModel<CartItems> add(@Valid @RequestBody CartItems cartobj)
	{		
		List<FoodItems> fi=cartobj.getListofitems();
		Iterator<FoodItems> itr1=fi.listIterator();
		Double total=0.0;
		fi.forEach(n->n.setFoodName(frepo.getById(n.getFoodId()).getFoodName()));
		fi.forEach(n->n.setFoodPrice(frepo.getById(n.getFoodId()).getFoodPrice()));
		fi.forEach(n->n.setStatus(frepo.getById(n.getFoodId()).getStatus()));
		cartobj.setListofitems(fi);
		int id=0;
		
		while(itr1.hasNext())
		{
			FoodItems f1=itr1.next();
			id=f1.getFoodId();
			total+=frepo.getById(id).getFoodPrice();
		}
		cartobj.setTotalprice(total);
		CartItems cobj=crepo.save(cartobj);
		EntityModel<CartItems> em=EntityModel.of(cobj);
		Link l=linkTo(methodOn(this.getClass()).showcart(cobj.getCartid())).withRel("view cart");
		Link l1=linkTo(methodOn(CustomerOrderingController.class).add(null, cobj.getCartid())).withRel("place order");
		em.add(l);
		em.add(l1);
		
		return em;
	}

	
	@GetMapping("/showcart/{id}")
	public CartItems showcart(@PathVariable int id)
	{	
		Optional<CartItems> cartobj=crepo.findById(id);
		if(!cartobj.isPresent())
			throw new CartNotFoundException("No Cart Found with id: "+id);
		
		return cartobj.get();
	}
	
	@DeleteMapping("/deletecart/{id}")
	public void delete(@PathVariable("id") int cartid)
	{	
		Optional<CartItems> cartobj=crepo.findById(cartid);
		if(!cartobj.isPresent())
			throw new CartNotFoundException("id: "+cartid);
		
		crepo.deleteById(cartid);
	}
	

	
}
