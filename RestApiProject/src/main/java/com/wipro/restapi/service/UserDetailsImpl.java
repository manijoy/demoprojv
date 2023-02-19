package com.wipro.restapi.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wipro.restapi.entity.User;

public class UserDetailsImpl implements UserDetails {

	private User user;
	
	
	
	public UserDetailsImpl(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		HashSet<SimpleGrantedAuthority> set= new HashSet();
		set.add(new SimpleGrantedAuthority(user.getuRole().getRolename()));
		
		return set;
	}

	@Override
	public String getPassword() {
		return user.getuPassword();
	}

	@Override
	public String getUsername() {
		return user.getuUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
