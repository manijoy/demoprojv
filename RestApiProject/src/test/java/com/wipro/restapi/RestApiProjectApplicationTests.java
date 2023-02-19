package com.wipro.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.restapi.entity.FoodItems;
import com.wipro.restapi.repository.FoodItemsRepository;

@SpringBootTest
class RestApiProjectApplicationTests {

	@Autowired
	FoodItemsRepository frepo;
	
	@Test
	@DisplayName("Creating FoodItem By Admin")
	void createFI() 
	{
		//RestTemplate template = new RestTemplate();
		FoodItems fobj = new FoodItems();
		fobj.setFoodId(100);
		fobj.setFoodName("Item-100");
		fobj.setFoodPrice(111.11);
		FoodItems f1=frepo.save(fobj);
		//FoodItems f1 =template.postForObject("http://localhost:8080/admin/createitem",fobj,FoodItems.class);
	    assertNotNull(fobj);
	    assertEquals(f1,fobj);
	}
}
