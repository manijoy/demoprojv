package com.wipro.restapi.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter{
	Logger log=LogManager.getLogger(JwtFilter.class.getName());
	@Autowired
	JwtUtility jwtutil;
	
	@Autowired
	UserDetailsService uservice;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String fulltoken=request.getHeader("Authorization");
		String token=null;
		String uname=null;
		if(fulltoken!=null && fulltoken.startsWith("Bearer "))
		{
			token=fulltoken.substring(7);
			try {
			uname=jwtutil.extractUsername(token);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			UserDetails uds=uservice.loadUserByUsername(uname);
			
			if(uname!=null &&SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UsernamePasswordAuthenticationToken unamePwdAuthToken=
						new UsernamePasswordAuthenticationToken(uds,null,uds.getAuthorities());
				
				unamePwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(unamePwdAuthToken);
			}
		}
		else
		{
			log.info("Invalid Token");
		}
		filterChain.doFilter(request, response);
	}

}
