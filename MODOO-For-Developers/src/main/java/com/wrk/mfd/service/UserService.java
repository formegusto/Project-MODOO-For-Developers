package com.wrk.mfd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.wrk.mfd.entity.User;
import com.wrk.mfd.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User readUser(UserDetails userDetails) {
		User vo = new User();
		vo.setId(userDetails.getUsername());
		
		return userRepository.readUser(vo);
	}
}
