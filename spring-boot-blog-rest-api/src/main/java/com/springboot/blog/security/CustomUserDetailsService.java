package com.springboot.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.User;
import com.springboot.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		//loading user from database by username
		User user = userRepo.findByEmail(username)
					.orElseThrow(()->new ResourceNotFoundException("User", "email: "+username, 0));
		
		
		return user;
	}
}	
	

