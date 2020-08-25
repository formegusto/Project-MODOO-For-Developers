package com.wrk.mfd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {
	@RequestMapping("/test")
	public Map<String, Object> test(String apikey) {
		Map<String, Object> returnMap = new HashMap<>();
				
		if(apikey.equals("test")) {
			{
				returnMap.put("result", "0");
				returnMap.put("value", "권한이 없는 apikey 입니다.");
			}
		}
		
		return returnMap;
	}
}
