package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.repository.UserRepository;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/postProduct")
	@PreAuthorize("hasAuthority('USER') or hasRole('MODERATOR')")
	public String postProduct(@RequestBody String body) {
		return "Product success posted";
	}
	
	@PostMapping("/partnerProduct")
	public String partnerProduct(@RequestBody String body) {
		return "Product partner success posted";
	}
}
