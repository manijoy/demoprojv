package com.wipro.restapi.controllers;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.restapi.entity.OrderEntity;
import com.wipro.restapi.exception.OrderNotFoundException;
import com.wipro.restapi.exception.StatusInvalidException;
import com.wipro.restapi.repository.OrderRepository;
import com.wipro.restapi.service.EmailService;


@RestController
@RequestMapping("/chef")
public class ChefController {
	
	@Autowired
	OrderRepository orepo;
	
	@GetMapping("/home")
	public String home()
	{
		return "Welcome to Chef Home Page";
	}
	
	//view orders
	@GetMapping("/vieworders")
	public List<OrderEntity> showorders()
	{
		
		List<OrderEntity> o1 =orepo.findByorderStatusLike("Order Taken");
		if(o1.isEmpty())
			throw new OrderNotFoundException("No Orders Available");
		return o1;
	}
	
	//updating order status
	@PutMapping("/updateorderstatus/{id}/{status}")
	public String update(@PathVariable("id") int orderid ,@PathVariable("status") String newstatus)
	{	
		Optional<OrderEntity> orobj=orepo.findById(orderid);
		if(!orobj.isPresent())
			throw new OrderNotFoundException("id: "+orderid);
		
		if(!newstatus.equals("Ready"))
			throw new StatusInvalidException("Status invalid :"+newstatus);
		
		
		OrderEntity o1=orobj.get();
		o1.setOrderStatus(newstatus);
		orepo.save(o1);
		String sub="!!!Order Status Updated!!!";
		String to=o1.getEmail();
		String msg="Order id is "+o1.getOrderId()+
				"\nOrder Status is "+o1.getOrderStatus()+
				"\nOrder Total Price inc GST - "+o1.getTotalPlusGST();
		EmailService.sendEmail(msg,sub,to);
		return "Order Status Updated";
			
	}
	
}
