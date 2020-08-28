package com.wrk.mfd.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wrk.mfd.entity.LogVO;
import com.wrk.mfd.entity.RequestDTO;
import com.wrk.mfd.repository.LogRepository;

@Service
public class LogService {
	@Autowired
	private LogRepository logRepository;
	
	public void insertLog(RequestDTO reqDTO, 
			Map<String, Object> returnMap) {
		LogVO log = new LogVO();
		
		// 결과 체크
		int result = (Integer) returnMap.get("result");
		if(result == 0) {
			log.setResult("FAIL");
		} else {
			log.setResult("SUCCESS");
		}
		log.setMessage((String) returnMap.get("message"));
		
		log.setMseq(reqDTO.getSeq());
		log.setApikey(reqDTO.getApikey());
		log.setType(reqDTO.getType());
		
		logRepository.insertLog(log);
	}
}
