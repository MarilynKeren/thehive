package com.thehive.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thehive.repositories.UserRepository;

//src/main/java/com/thehive/service/CustomUserDetailsService.java
@Service
public class CustomUserDetailsService implements UserDetailsService {
 
 private final UserRepository userRepository; // JPA repo lives here

 public CustomUserDetailsService(UserRepository userRepository) {
	 this.userRepository = userRepository;
 }

 @Override
 public UserDetails loadUserByUsername(String username) {
     return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
 }
}
