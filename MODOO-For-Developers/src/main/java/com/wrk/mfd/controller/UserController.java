package com.wrk.mfd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wrk.mfd.entity.User;
import com.wrk.mfd.repository.UserRepository;

@Controller
@RequestMapping("/user/api")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/signup")
	public String signup(User vo) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		vo.setPassword(encoder.encode(vo.getPassword()));
		vo.setRole("ROLE_MEMBER");
		
		userRepository.signupUser(vo);
		
		return "redirect:/user/signin";
	}
}
