package com.wipro.restapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wipro.restapi.service.UserDetailsServiceImpl;
import com.wipro.restapi.util.JwtFilter;



@EnableWebSecurity
@Configuration
public class JwtSecurityConfiguration  extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsServiceImpl usedi;
	
	@Autowired
	JwtFilter jwtfilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String [] wr= 
		{
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/v2/api-docs",
				"/auth/**",
				"/register",
				"h2-console/**",
				"/webjars/**"
		};
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers(wr).permitAll()
		.antMatchers("/token").permitAll()
		.antMatchers("/createuser").permitAll()
		.antMatchers("/customer/**").hasRole("NORMAL")
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/chef/**").hasRole("CHEF")
		.anyRequest()
		.authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
		http.addFilterBefore(jwtfilter,UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usedi).passwordEncoder(pwdEncoder());
		}
	
	@Bean
	public BCryptPasswordEncoder pwdEncoder()
	{
		return new BCryptPasswordEncoder(10);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
}
