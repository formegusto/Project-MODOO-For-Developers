package com.wrk.mfd.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wrk.mfd.entity.RequestDTO;
import com.wrk.mfd.service.ApiService;

@RestController
@RequestMapping("/api")
public class RestApiController {
	@Autowired
	private ApiService apiService;
	
	@GetMapping("/info")
	@ResponseBody
	public Map<String, Object> info(RequestDTO reqDTO) {
		reqDTO.setType("info");
		return apiService.readInfoData(reqDTO);
	}
	
	@GetMapping("/frame")
	@ResponseBody
	public Map<String, Object> frame(RequestDTO reqDTO) {
		reqDTO.setType("frame");
		return apiService.readFrameData(reqDTO);
	}
}
