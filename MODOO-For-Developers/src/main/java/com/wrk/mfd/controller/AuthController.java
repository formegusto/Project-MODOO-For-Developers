package com.wrk.mfd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wrk.mfd.entity.User;
import com.wrk.mfd.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/modooAuth")
	public String modooAuth(@AuthenticationPrincipal UserDetails userDetails,
			Model model) {
		User user = userService.readUser(userDetails);
		
		model.addAttribute("user", user);
		
		return "auth/modooAuth";
	}
}
