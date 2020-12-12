package com.example.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.test.config.security.JwtAccessDeniedHandler;
import com.example.test.config.security.JwtAuthenticationEntryPoint;
import com.example.test.config.security.JwtAuthenticationFilter;
import com.example.test.config.security.JwtAuthorizationFilter;
import com.example.test.repository.UserRepository;
import com.example.test.services.security.UserDetailsServiceImpl;







@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(		
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
	  @Autowired
	    private JwtAuthenticationEntryPoint authenticationErrorHandler;
	
	  @Autowired
	    private JwtAccessDeniedHandler jwtAccessDeniedHandler;  
	  
	  //Why i need this???
	  @Autowired
	    UserRepository userRepository;
	  
	//Why i need this???
	  @Bean
	    public UserRepository userRepo() {
	        return userRepository;
	    }

	   @Bean
	    public static PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	   
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		 
		http.cors().and().csrf().disable()
			.exceptionHandling()
        	.authenticationEntryPoint(authenticationErrorHandler)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()			
			.authorizeRequests()
			.antMatchers("/h2/**").permitAll()
			.antMatchers(HttpMethod.POST,"/api/product/**","/api/login","/api/hacker/testJson").permitAll()
			.and()			
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), getApplicationContext()))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
            .sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
                
    }
}
