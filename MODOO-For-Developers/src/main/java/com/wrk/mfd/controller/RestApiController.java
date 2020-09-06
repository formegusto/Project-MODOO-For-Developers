package com.wrk.mfd.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Map<String, Object> readInfo(RequestDTO reqDTO) {
		reqDTO.setType("info");
		return apiService.readInfoData(reqDTO);
	}
	
	@GetMapping("/frame")
	@ResponseBody
	public Map<String, Object> readFrame(RequestDTO reqDTO) {
		reqDTO.setType("frame");
		return apiService.readFrameData(reqDTO);
	}
	
	@PostMapping("/info")
	@ResponseBody
	public Map<String, Object> postInfo(@RequestBody RequestDTO reqDTO) {
		reqDTO.setType("info");
		return apiService.postInfoData(reqDTO);
	}
	
	@PostMapping("/frame")
	@ResponseBody
	public Map<String, Object> postFrame(@RequestBody RequestDTO reqDTO){
		reqDTO.setType("frame");
		return apiService.postFrameData(reqDTO);
	}
	
	@DeleteMapping("/info")
	@ResponseBody
	public Map<String, Object> deleteInfo(RequestDTO reqDTO) {
		reqDTO.setType("info");
		return apiService.deleteInfoData(reqDTO);
	}
	
	@DeleteMapping("/frame")
	@ResponseBody
	public Map<String, Object> deleteFrame(RequestDTO reqDTO) {
		reqDTO.setType("frame");
		return apiService.deleteFrameData(reqDTO);
	}
}
