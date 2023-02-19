package com.wipro.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wipro.restapi.entity.User;
import com.wipro.restapi.exception.UserNotfoundException;
import com.wipro.restapi.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository urepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=urepo.findById(username).get();
		if(user==null)
		{
			throw new UserNotfoundException("User Not Found" +username);
		}
		return new UserDetailsImpl(user);
	}
}
