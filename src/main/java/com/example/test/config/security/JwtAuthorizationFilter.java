package com.example.test.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.test.model.User;
import com.example.test.repository.UserRepository;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


	private UserRepository userRepository;
	private String username;
	
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    //Verify Token Bearer
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println("=============== GOING TO AUTHORIZED =================");
        if (token == null) return null;
        try {
            username = JWT.require(Algorithm.HMAC512("terterteydfsdfs".getBytes()))
                    .build()
                    .verify(token.split(" ")[1])
                    .getSubject();
        } catch (Exception err){
            err.printStackTrace();
        }
        if (username == null) return null;

        User user = userRepository.findByUsername(username)
        		.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        
  
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
		
		//When using JWT add authorities when validating token jwt
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    //Continue to next filter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

        if (authenticationToken == null) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
    
    private List<GrantedAuthority> buildUserAuthority(String userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		String [] userRolesArray = userRoles.split("\\s*,\\s*");		
		List<String> userRolesList = Arrays.asList(userRolesArray);
		
	    for (String role : userRolesList) {
	        authorities.add(new SimpleGrantedAuthority(role));
	    }

	    return authorities;
	}
}
