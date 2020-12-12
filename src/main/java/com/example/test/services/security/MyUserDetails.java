package com.example.test.services.security;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

 
public class MyUserDetails extends User {
 
	public MyUserDetails(String username, String password, boolean enabled,
	        boolean accountNonExpired, boolean credentialsNonExpired,
	        boolean accountNonLocked,String emailId,
	        Collection<? extends GrantedAuthority> authorities) {
	    super(username, password, enabled, accountNonExpired,
	            credentialsNonExpired, accountNonLocked, authorities);
	    // TODO Auto-generated constructor stub
	}
}