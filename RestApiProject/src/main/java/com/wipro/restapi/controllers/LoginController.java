package com.wipro.restapi.controllers;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wipro.restapi.entity.Details;
import com.wipro.restapi.entity.Role;
import com.wipro.restapi.entity.User;
import com.wipro.restapi.exception.UserNameException;
import com.wipro.restapi.repository.UserRepository;
import com.wipro.restapi.service.UserDetailsServiceImpl;
import com.wipro.restapi.util.JwtRequest;
import com.wipro.restapi.util.JwtResponse;
import com.wipro.restapi.util.JwtUtility;


@RestController
public class LoginController {
	
	public static final Logger log=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	JwtUtility jutil;
	
	@Autowired
	UserRepository urepo;
		
	@Autowired
	UserDetailsServiceImpl uservice;
	
	@Autowired
	AuthenticationManager authmanager;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	
	//login token
	@PostMapping("/token")
	public ResponseEntity<Object> createToken(@RequestBody JwtRequest request) throws Exception
	{

		try {
			authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		}
		catch(BadRequest e)
		{
			e.printStackTrace();
			throw new Exception("Bad Credientials");
		}
		UserDetails udservice=uservice.loadUserByUsername(request.getUsername());
		String token=jutil.generateToken(udservice);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	//create new user
	@PostMapping("/register")
	public ResponseEntity<Object> createUser(@RequestBody Details det)
	{
		List<User> l=urepo.findAll();
		Iterator<User> i=l.iterator();
		while(i.hasNext())
		{
			if(i.next().getuUsername().equalsIgnoreCase(det.getUname()))
			{
				throw new UserNameException("Username already exists "+det.getUname());
			}
			
		}
		User user1=new User();
		user1.setuUsername(det.getUname());
		user1.setuEmail(det.getEmail());
		user1.setuPassword(bcrypt.encode(det.getPassword()));
		user1.setuRole(new Role(2,"ROLE_NORMAL"));
		urepo.save(user1);
		URI loc=ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/token").buildAndExpand().toUri();
		return ResponseEntity.created(loc).body("User ID Created");
		
	}
	
	
}
