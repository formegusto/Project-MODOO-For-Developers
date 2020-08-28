package com.wrk.mfd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wrk.mfd.entity.Key;
import com.wrk.mfd.entity.LogVO;
import com.wrk.mfd.entity.RequestVO;
import com.wrk.mfd.entity.User;
import com.wrk.mfd.repository.LogRepository;
import com.wrk.mfd.repository.ReqRepository;
import com.wrk.mfd.service.KeyService;
import com.wrk.mfd.service.UserService;

@Controller
public class ApiController {
	@Autowired
	private UserService userService;
	@Autowired
	private KeyService keyService;
	@Autowired
	private ReqRepository reqRepository;
	@Autowired
	private LogRepository logRepository;
	
	@GetMapping("/")
	public String mainPage(@AuthenticationPrincipal UserDetails userDetails,
			Model model) {
		User user = userService.readUser(userDetails);
		Key key = keyService.getApikey(userDetails);
		
		Map<String, Object> payload = new HashMap<>();
		
		{
			payload.put("user_id", user.getModoo_id());
			payload.put("type", "info");
		}
		List<RequestVO> infoReqList = reqRepository.readReq(payload);
		
		{
			payload.clear();
			payload.put("user_id", user.getModoo_id());
			payload.put("type", "frame");
		}
		List<RequestVO> frameReqList = reqRepository.readReq(payload);
		
		{
			payload.clear();
			payload.put("user_id", user.getModoo_id());
			payload.put("apikey", key.getApikey());
			payload.put("start", 0);
			payload.put("limit", 5);
		}
		List<LogVO> logList = logRepository.readLog(payload);
		System.out.println(logList);
		
		model.addAttribute("logList", logList);
		model.addAttribute("infoList", infoReqList);
		model.addAttribute("frameList", frameReqList);
		model.addAttribute("user", user);
		model.addAttribute("key", key);
		
		return "/main";
	}
	
	@GetMapping("/datamgmt")
	public String datamgmt(@AuthenticationPrincipal UserDetails userDetails,
			Model model) {
		User user = userService.readUser(userDetails);
		
		model.addAttribute("infoList", reqRepository.readMinfo(user.getModoo_id()));
		model.addAttribute("frameList", reqRepository.readMframe(user.getModoo_id()));
		model.addAttribute("user", user);
		
		return "/datamgmt";
	}
	
	@GetMapping("/logmgmt")
	public String logmgmt(@AuthenticationPrincipal UserDetails userDetails,
			Model model, @RequestParam(defaultValue = "1") int page) {
		User user = userService.readUser(userDetails);
		Key key = keyService.getApikey(userDetails);
		
		Map<String, Object> payload = new HashMap<>();
		{
			payload.clear();
			payload.put("user_id", user.getModoo_id());
			payload.put("apikey", key.getApikey());
			payload.put("start", (page-1) * 10);
			payload.put("limit", 10);
		}
		List<LogVO> logList = logRepository.readLog(payload);
		
		Boolean isLastPage = false;
		if(logList.size() < 10) {
			isLastPage = true;
		} else {
			{
				payload.clear();
				payload.put("user_id", user.getModoo_id());
				payload.put("apikey", key.getApikey());
				payload.put("start", (page) * 10);
				payload.put("limit", 10);
			}
			List<LogVO> tmpList = logRepository.readLog(payload);
			if(tmpList.size() == 0) {
				isLastPage = true;
			}
		}
		
		model.addAttribute("isLastPage", isLastPage);
		model.addAttribute("page", page);
		model.addAttribute("logList", logList);
		model.addAttribute("user", user);
		
		return "/logmgmt";
	}
	
	@GetMapping("/authCheck")
	public String authCheck(@AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.readUser(userDetails);
		String user_role = user.getRole();
		String page = "redirect:/";
		
		if(user_role.equals("ROLE_UNAUTH")) {
			page = "redirect:/auth/modooAuth";
		} 
		
		return page;
	}
}
