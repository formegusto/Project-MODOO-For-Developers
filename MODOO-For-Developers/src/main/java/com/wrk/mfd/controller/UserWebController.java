package com.wrk.mfd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserWebController {
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
}
