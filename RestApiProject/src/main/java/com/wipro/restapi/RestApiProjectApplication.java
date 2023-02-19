package com.wipro.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wipro.restapi.entity.FoodItems;
import com.wipro.restapi.entity.Role;
import com.wipro.restapi.entity.User;
import com.wipro.restapi.repository.FoodItemsRepository;
import com.wipro.restapi.repository.RoleRepository;
import com.wipro.restapi.repository.UserRepository;

@SpringBootApplication
public class RestApiProjectApplication implements CommandLineRunner{

	@Autowired
	UserRepository urepo;
	
	@Autowired
	RoleRepository rrepo;
	
	@Autowired
	FoodItemsRepository frepo;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RestApiProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
				
		User user1=new User();
		user1.setuUsername("admin");
		user1.setuEmail("admin@gmail.com");
		user1.setuPassword(bcrypt.encode("admin"));
		user1.setuRole(new Role(1,"ROLE_ADMIN"));
		urepo.save(user1);
		
		User user2=new User();
		user2.setuUsername("user1");
		user2.setuEmail("user1@gmail.com");
		user2.setuPassword(bcrypt.encode("user1"));
		user2.setuRole(new Role(2,"ROLE_NORMAL"));
		urepo.save(user2);
				
		User user4=new User();
		user4.setuUsername("chef1");
		user4.setuEmail("chef1@gmail.com");
		user4.setuPassword(bcrypt.encode("chef1"));
		user4.setuRole(new Role(3,"ROLE_CHEF"));
		urepo.save(user4);
		
		String a="Available";
		FoodItems f1=new FoodItems();
		f1.setFoodId(1);
		f1.setFoodName("Item-1");
		f1.setFoodPrice(100.00);
		f1.setStatus(a);
		frepo.save(f1);
		
		FoodItems f2=new FoodItems();
		f2.setFoodId(2);
		f2.setFoodName("Item-2");
		f2.setFoodPrice(200.00);
		f2.setStatus(a);
		frepo.save(f2);
		
		FoodItems f3=new FoodItems();
		f3.setFoodId(3);
		f3.setFoodName("Item-3");
		f3.setFoodPrice(300.00);
		f3.setStatus(a);
		frepo.save(f3);
		
		FoodItems f100=new FoodItems();
		f100.setFoodId(100);
		f100.setFoodName("Item-100");
		f100.setFoodPrice(700.00);
		f100.setStatus(a);
		frepo.save(f100);
		
	}

}
