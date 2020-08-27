package com.wrk.mfd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wrk.mfd.entity.ModooData;
import com.wrk.mfd.entity.ModooFHI;
import com.wrk.mfd.entity.ModooFrame;
import com.wrk.mfd.entity.ModooInfo;
import com.wrk.mfd.entity.RequestDTO;
import com.wrk.mfd.entity.User;
import com.wrk.mfd.repository.KeyRepository;
import com.wrk.mfd.repository.MdataRepository;
import com.wrk.mfd.repository.MframeRepository;
import com.wrk.mfd.repository.MinfoRepository;
import com.wrk.mfd.repository.UserRepository;

@Service
public class ApiService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KeyRepository keyRepository;
	@Autowired
	private MinfoRepository minfoRepository;
	@Autowired
	private MframeRepository mframeRepository;
	@Autowired
	private MdataRepository mdataRepository;
	
	public Map<String, Object> readInfoData(RequestDTO reqDTO){
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0) {
			{
				returnMap.put("result", 0);
				returnMap.put("msg", "잘못된 파라미터 입니다.");
			}
			
			return returnMap;
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("msg", "유효하지 않은 API Key 입니다.");
			}
			
			return returnMap;
		}
		
		// getUser
		int user_id = keyRepository.readUserId(reqDTO.getApikey());
		User findUser = new User();
		findUser.setSeq(user_id);
		findUser = userRepository.readUser(findUser);
		
		// SEQ 유효성 검사
		ModooInfo minfo = new ModooInfo();
		minfo.setIseq(reqDTO.getSeq());
		if(minfoRepository.checkSeq(minfo)) {
			minfo.setId(findUser.getModoo_id());
			if(!minfoRepository.checkSeq(minfo)) {
				{
					returnMap.put("result", 0);
					returnMap.put("message", "권한이 없는 INFO 입니다.");
				}
				
				return returnMap;
			} else {
				minfo = minfoRepository.readMinfo(minfo);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 INFO 입니다.");
			}
				
			return returnMap;
		}
		
		// 데이터 구성
		ModooData mdata = new ModooData();
		mdata.setIseq(minfo.getIseq());
		{
			returnMap.put("result", 1);
			returnMap.put("datalist", mdataRepository.readMdata(mdata));
		}
		
		return returnMap;
	}
	
	public Map<String, Object> readFrameData(RequestDTO reqDTO){
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0) {
			{
				returnMap.put("result", 0);
				returnMap.put("msg", "잘못된 파라미터 입니다.");
			}
			
			return returnMap;
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("msg", "유효하지 않은 API Key 입니다.");
			}
			
			return returnMap;
		}
		
		// getUser
		int user_id = keyRepository.readUserId(reqDTO.getApikey());
		User findUser = new User();
		findUser.setSeq(user_id);
		findUser = userRepository.readUser(findUser);
		
		// SEQ 유효성 검사
		ModooFrame mframe = new ModooFrame();
		mframe.setFseq(reqDTO.getSeq());
		if(mframeRepository.checkSeq(mframe)) {
			mframe.setId(findUser.getModoo_id());
			if(!mframeRepository.checkSeq(mframe)) {
				{
					returnMap.put("result", 0);
					returnMap.put("message", "권한이 없는 FRAME 입니다.");
				}
				
				return returnMap;
			} else {
				mframe = mframeRepository.readMframe(mframe);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 FRAME 입니다.");
			}
				
			return returnMap;
		}
		
		List<ModooFHI> fhiList = mframeRepository.readFHI(mframe);
		ModooInfo minfo;
		ModooInfo ivo;
		ModooData dvo;
		Map<String,Object> dataMap;
		List<Map<String, Object>> infoList = new ArrayList<>();
		for(ModooFHI fhi : fhiList) {
			ivo = new ModooInfo();
			ivo.setIseq(fhi.getIseq());
			
			minfo = minfoRepository.readMinfo(ivo);
			
			dvo = new ModooData();
			dvo.setIseq(minfo.getIseq());
			dataMap = new HashMap<>();
			{
				dataMap.put("field", minfo.getField());
				dataMap.put("datalist", mdataRepository.readMdata(dvo));
			}
			infoList.add(dataMap);
		}
		
		// 데이터 구성
		{
			returnMap.put("result", 1);
			returnMap.put("infolist", infoList);
		}
		
		return returnMap;
	}
}
