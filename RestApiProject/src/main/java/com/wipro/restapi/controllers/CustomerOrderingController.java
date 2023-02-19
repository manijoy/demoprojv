package com.wipro.restapi.controllers;

import java.net.URI;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wipro.restapi.entity.CartItems;
import com.wipro.restapi.entity.OrderEntity;
import com.wipro.restapi.entity.User;
import com.wipro.restapi.exception.CartNotFoundException;
import com.wipro.restapi.exception.OrderNotFoundException;
import com.wipro.restapi.exception.PhoneNumberInvalidException;
import com.wipro.restapi.repository.CartItemsRepository;
import com.wipro.restapi.repository.OrderRepository;
import com.wipro.restapi.repository.UserRepository;
import com.wipro.restapi.service.EmailService;
import com.wipro.restapi.service.GenerateInvoice;

@RestController
@RequestMapping("/customer")
public class CustomerOrderingController {
	
	@Autowired
	CartItemsRepository crepo;
	
	@Autowired
	OrderRepository orepo;

	@Autowired
	UserRepository urepo;
	
	@PostMapping("/placeorder/{cartid}")
	public ResponseEntity<Object> add(@Valid @RequestBody OrderEntity orderobj,@PathVariable("cartid") int cartid)
	{
		UserDetails ud=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<CartItems> cartobj=crepo.findById(cartid);
		if(!cartobj.isPresent())
			throw new CartNotFoundException("id: "+cartid);
		
		String phpattern="^[6-9][0-9]{9}";
		if(!orderobj.getPhno().matches(phpattern))
			throw new PhoneNumberInvalidException("Invalid Phone No: "+orderobj.getPhno());
		
		User u;
		u=urepo.getById(ud.getUsername());
		
		CartItems c1=cartobj.get();
		double p=c1.getTotalprice()+c1.getTotalprice()*18/100;
		orderobj.setTotalPlusGST(p);
		orderobj.setOrderDate(new Date());
		orderobj.setOrderStatus("Order Taken");
		orderobj.setCart(c1);
		orderobj.setEmail(u.getuEmail());
		orderobj.setUser(u);
		orepo.save(orderobj);
		
		//email sub and msg
		String sub="Order Placed Successfully";
		String to=u.getuEmail();
		String msg="Order Details are ";
		String file=GenerateInvoice.returnfilename(orderobj);
		msg=msg+"\nOrder Id - "+orderobj.getOrderId();
		msg=msg+"\nOrder Status - "+orderobj.getOrderStatus();
		msg=msg+"\nOrder Items are - "+orderobj.getCart().getListofitems();
		EmailService.sendEmail(msg,sub,to,file);
		
		URI loc=ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("customer/showorderdetails/{id}").buildAndExpand(orderobj.getOrderId()).toUri();
		return ResponseEntity.created(loc).body("Order Id is "+orderobj.getOrderId());
		
	}
	
	@GetMapping("/showorderdetails/{id}")
	public Optional<OrderEntity> show(@PathVariable("id") int orderid)
	{	
		UserDetails ud=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<OrderEntity> ordobj=orepo.findById(orderid);
		if(!ordobj.isPresent() | !(ordobj.get().getUser().getuUsername().equals(ud.getUsername())))
			throw new OrderNotFoundException("Order Not Found with id: "+orderid);
		return orepo.findById(orderid);
	}
		
}
