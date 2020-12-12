package com.example.test.config.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.test.repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;



public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        this.authenticationManager = authenticationManager;
        this.setFilterProcessesUrl("/api/login");
        
        //Why i need this???
        this.userRepository = ctx.getBean("userRepo", UserRepository.class);
    }

    //Check If User Is Valid
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            System.out.println("=============== GOING TO AUTHENTICATE =================");

        	com.example.test.model.User user = new ObjectMapper().readValue(request.getInputStream(), com.example.test.model.User.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            		user.getUsername(),
            		user.getPassword(),
                    new ArrayList<>()
            );
            System.out.println("======"+user.getUsername());
            System.out.println("======"+user.getPassword());
            return authenticationManager.authenticate(token);
        }
		 catch (IOException e) {
		    throw new RuntimeException(e);
		}
        
    }

    //Create JWT Token if Auth Success
    @Override
    public void successfulAuthentication(HttpServletRequest request,
                                         HttpServletResponse response,
                                         FilterChain chain,
                                         Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        System.out.println("================"+username);
        com.example.test.model.User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("username not found"));

        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 8567567))
                .sign(Algorithm.HMAC512("terterteydfsdfs".getBytes()));

   

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(token);
    }

    //Response if JWT is invalid
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

       

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(400);
        response.getWriter().write("error login");
        
    }
}
