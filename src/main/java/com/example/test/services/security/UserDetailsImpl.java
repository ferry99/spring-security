package com.example.test.services.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	
	private User user;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String email;

	@JsonIgnore
	private String password;
	

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(User user) {
		this.user = user;
	}
	
	public UserDetailsImpl(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

//	public static UserDetailsImpl build(User user) {
//		
//		List<String> roles = user.getRoles();
//		List<GrantedAuthority> authorities = new ArrayList<>();
//	     System.out.println(user.toString());
//	    for (String role : roles) {
//	        authorities.add(new SimpleGrantedAuthority(role));
//	    }
//		
//
//		return new UserDetailsImpl(
//				user.getUsername(), 
//				user.getPassword(), 
//				authorities);
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("admin"));
//		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}