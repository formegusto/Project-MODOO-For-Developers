package com.wrk.mfd.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wrk.mfd.entity.RequestDTO;
import com.wrk.mfd.service.ApiService;

@RestController
@RequestMapping("/api")
public class RestApiController {
	@Autowired
	private ApiService apiService;
	
	@RequestMapping("/info")
	public Map<String, Object> info(RequestDTO reqDTO) {
		System.out.println(reqDTO);
		
		return apiService.readInfoData(reqDTO);
	}
	
	@RequestMapping("/frame")
	public Map<String, Object> frame(RequestDTO reqDTO) {
		System.out.println(reqDTO);
		
		return apiService.readFrameData(reqDTO);
	}
}
