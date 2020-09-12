package com.wrk.mfd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wrk.mfd.entity.User;
import com.wrk.mfd.repository.UserRepository;
import com.wrk.mfd.service.KeyService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	KeyService keyService;
	
	@PostMapping("/signup/do")
	public String signup(User vo) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		vo.setPassword(encoder.encode(vo.getPassword()));
		
		userRepository.signupUser(vo);
		
		return "redirect:/user/signin";
	}
	
	@GetMapping("/signin")
	public String signin() {
		return "user/signin";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "user/signup";
	}
	
	@GetMapping("/reissueKey")
	public String reissueKey(@AuthenticationPrincipal UserDetails userDetails) {
		keyService.reIssueApiKey(userDetails);
		
		return "redirect:/";
	}
}
