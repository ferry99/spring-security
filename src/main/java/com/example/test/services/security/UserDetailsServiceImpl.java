package com.example.test.services.security;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.test.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.example.test.model.User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		System.out.println("LEAKED PASS : " + user.getPassword());
		
		//Create Mock Roles
		List<String> roles = new ArrayList<>();
		roles.add("ADMIN");		
		List<GrantedAuthority> authorities = buildUserAuthority(roles);
		//Build UserDetails Implementation
		
		//When using basic put authorities rule into here 
//		return buildUserForAuthentication(user, authorities);
	    return buildUserForAuthentication(user, new ArrayList<>());
	}
	
	private User buildUserForAuthentication(com.example.test.model.User user, List<GrantedAuthority> authorities) {
	     MyUserDetails myUserDetails = new MyUserDetails (user.getUsername(), user.getPassword(), true, true ,true , true  , "email@email.com" , authorities);
	     return myUserDetails;
	}

	private List<GrantedAuthority> buildUserAuthority(List<String> userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
	    for (String role : userRoles) {
	        authorities.add(new SimpleGrantedAuthority(role));
	    }

	    return authorities;
	}

	

}