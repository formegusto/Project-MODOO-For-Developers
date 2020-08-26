package com.wrk.mfd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wrk.mfd.entity.ModooUser;
import com.wrk.mfd.entity.User;
import com.wrk.mfd.service.KeyService;
import com.wrk.mfd.service.MuserService;
import com.wrk.mfd.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private MuserService muserService;
	@Autowired
	private KeyService keyService;
	
	@GetMapping("/modooAuth")
	public String modooAuth(@AuthenticationPrincipal UserDetails userDetails,
			Model model) {
		User user = userService.readUser(userDetails);
		
		model.addAttribute("user", user);
		
		return "auth/modooAuth";
	}
	
	@PostMapping("/modooAuth")
	public String modooAuth_post(ModooUser muser,
			Model model) {
		ModooUser userCheck = muserService.authMuser(muser);
		
		if(userCheck==null) {
			return "auth/failed";
		} else {
			model.addAttribute("muser", muser);
			
			return "auth/modooAuthConfirm";
		}
	}
	
	@PostMapping("/modooAuth/register")
	public String modooAuthRegister(@AuthenticationPrincipal UserDetails userDetails, 
			ModooUser muser) {
			keyService.registerAuth(userDetails, muser);
			
			return "redirect:/";
	}
}
