package com.example.test.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.test.model.User;
import com.example.test.repository.UserRepository;

@Service
public class DbInit implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder;
	
	public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		System.out.println("CONSTRUCTING COMMAND LINE RUNNER");
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
//		this.userRepository.deleteAll();
//		
//		User admin = new User("admin", passwordEncoder.encode("admin"), "ADMIN,USER");
//		User partner = new User("partner", passwordEncoder.encode("partner") , "PARTNER");
//		
//		List<User> userList = Arrays.asList(admin, partner);
//		
//		this.userRepository.saveAll(userList);
		
	}

	
}
