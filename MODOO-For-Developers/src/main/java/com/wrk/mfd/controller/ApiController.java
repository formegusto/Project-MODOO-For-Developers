package com.wrk.mfd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.wrk.mfd.entity.User;
import com.wrk.mfd.service.UserService;

@Controller
public class ApiController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String mainPage() {
		return "/main";
	}
	
	@GetMapping("/authCheck")
	public String authCheck(@AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.readUser(userDetails);
		String user_role = user.getRole();
		String page = "/";
		
		if(user_role.equals("ROLE_UNAUTH")) {
			page = "redirect:/auth/modooAuth";
		} 
		
		return page;
	}
}
