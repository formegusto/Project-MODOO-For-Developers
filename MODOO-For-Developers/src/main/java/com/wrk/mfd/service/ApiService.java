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
import com.wrk.mfd.entity.RequestVO;
import com.wrk.mfd.entity.User;
import com.wrk.mfd.repository.KeyRepository;
import com.wrk.mfd.repository.MdataRepository;
import com.wrk.mfd.repository.MframeRepository;
import com.wrk.mfd.repository.MinfoRepository;
import com.wrk.mfd.repository.ReqRepository;
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
	@Autowired
	private ReqRepository reqRepository;
	@Autowired
	private LogService logService;
	
	public Map<String, Object> readInfoData(RequestDTO reqDTO){
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "잘못된 파라미터 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// Request 요청 검사
		RequestVO reqVO = new RequestVO();
		reqVO.setMseq(reqDTO.getSeq());
		reqVO.setType(reqDTO.getType());
		
		if(!reqRepository.checkReq(reqVO)) {
			ModooInfo mivo = new ModooInfo();
			mivo.setIseq(reqDTO.getSeq());
			
			mivo = minfoRepository.readMinfo(mivo);
			if(mivo != null) {
				reqVO.setTitle(mivo.getTitle());
				reqRepository.createReq(reqVO);
			}
		} else {
			reqRepository.increaseCnt(reqVO);
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "유효하지 않은 API Key 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
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
				logService.insertLog(reqDTO, returnMap);
				return returnMap;
			} else {
				minfo = minfoRepository.readMinfo(minfo);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 INFO 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// 데이터 구성
		ModooData mdata = new ModooData();
		mdata.setIseq(minfo.getIseq());
		{
			returnMap.put("result", 1);
			returnMap.put("message", "성공");
			returnMap.put("datalist", mdataRepository.readMdata(mdata));
		}
		logService.insertLog(reqDTO, returnMap);
		return returnMap;
	}
	
	public Map<String, Object> readFrameData(RequestDTO reqDTO){
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "잘못된 파라미터 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// Request 요청 검사
		RequestVO reqVO = new RequestVO();
		reqVO.setMseq(reqDTO.getSeq());
		reqVO.setType(reqDTO.getType());
				
		if(!reqRepository.checkReq(reqVO)) {
			ModooFrame mfvo = new ModooFrame();
			mfvo.setFseq(reqDTO.getSeq());
				
			mfvo = mframeRepository.readMframe(mfvo);
			if(mfvo != null) {
				reqVO.setTitle(mfvo.getTitle());
				reqRepository.createReq(reqVO);
			}
		} else {
			reqRepository.increaseCnt(reqVO);
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "유효하지 않은 API Key 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
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
				logService.insertLog(reqDTO, returnMap);
				return returnMap;
			} else {
				mframe = mframeRepository.readMframe(mframe);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 FRAME 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);	
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
			returnMap.put("message", "성공");
			returnMap.put("infolist", infoList);
		}
		logService.insertLog(reqDTO, returnMap);
		return returnMap;
	}
	
	public Map<String, Object> postInfoData(RequestDTO reqDTO) {
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0 || reqDTO.getData() == null) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "잘못된 파라미터 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// Request 요청 검사
		RequestVO reqVO = new RequestVO();
		reqVO.setMseq(reqDTO.getSeq());
		reqVO.setType(reqDTO.getType());
		
		if(!reqRepository.checkReq(reqVO)) {
			ModooInfo mivo = new ModooInfo();
			mivo.setIseq(reqDTO.getSeq());
			
			mivo = minfoRepository.readMinfo(mivo);
			if(mivo != null) {
				reqVO.setTitle(mivo.getTitle());
				reqRepository.createReq(reqVO);
			}
		} else {
			reqRepository.increaseCnt(reqVO);
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "유효하지 않은 API Key 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
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
				logService.insertLog(reqDTO, returnMap);
				return returnMap;
			} else {
				minfo = minfoRepository.readMinfo(minfo);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 INFO 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// 데이터 개수 유효성 검사
		if(reqDTO.getData().size() != 1) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "데이터 개수가 맞지 않습니다.");
			}
			logService.insertLog(reqDTO, returnMap);	
			return returnMap;
		}
		
		ModooData mData = new ModooData();
		mData.setIseq(reqDTO.getSeq());
		mData.setData(reqDTO.getData().get(0));
		
		mdataRepository.postMdata(mData);
		
		// 데이터 구성
		{
			returnMap.put("result", 1);
			returnMap.put("message", "성공");
		}
		logService.insertLog(reqDTO, returnMap);
		
		return returnMap;
	}
	
	public Map<String, Object> postFrameData(RequestDTO reqDTO) {
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0 || reqDTO.getData() == null) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "잘못된 파라미터 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// Request 요청 검사
		RequestVO reqVO = new RequestVO();
		reqVO.setMseq(reqDTO.getSeq());
		reqVO.setType(reqDTO.getType());
				
		if(!reqRepository.checkReq(reqVO)) {
			ModooFrame mfvo = new ModooFrame();
			mfvo.setFseq(reqDTO.getSeq());
				
			mfvo = mframeRepository.readMframe(mfvo);
			if(mfvo != null) {
				reqVO.setTitle(mfvo.getTitle());
				reqRepository.createReq(reqVO);
			}
		} else {
			reqRepository.increaseCnt(reqVO);
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "유효하지 않은 API Key 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
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
				logService.insertLog(reqDTO, returnMap);
				return returnMap;
			} else {
				mframe = mframeRepository.readMframe(mframe);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 FRAME 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);	
			return returnMap;
		}
		
		// 데이터 개수 유효성 검사
		List<ModooFHI> fhiList = mframeRepository.readFHI(mframe);
		if(reqDTO.getData().size() != fhiList.size()) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "데이터 개수가 맞지 않습니다.");
			}
			logService.insertLog(reqDTO, returnMap);	
			return returnMap;
		}
		
		List<String> dataList = reqDTO.getData();
		int idx = 0;
		for(ModooFHI fhi : fhiList) {
			ModooData mData = new ModooData();
			mData.setIseq(fhi.getIseq());
			mData.setData(dataList.get(idx));
			
			mdataRepository.postMdata(mData);
			idx++;
		}
		
		
		// 데이터 구성
		{
			returnMap.put("result", 1);
			returnMap.put("message", "성공");
		}
		logService.insertLog(reqDTO, returnMap);
		
		return returnMap;
	}
	
	public Map<String, Object> deleteInfoData(RequestDTO reqDTO) {
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0 || reqDTO.getIdx() == null) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "잘못된 파라미터 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// Request 요청 검사
		RequestVO reqVO = new RequestVO();
		reqVO.setMseq(reqDTO.getSeq());
		reqVO.setType(reqDTO.getType());
		
		if(!reqRepository.checkReq(reqVO)) {
			ModooInfo mivo = new ModooInfo();
			mivo.setIseq(reqDTO.getSeq());
			
			mivo = minfoRepository.readMinfo(mivo);
			if(mivo != null) {
				reqVO.setTitle(mivo.getTitle());
				reqRepository.createReq(reqVO);
			}
		} else {
			reqRepository.increaseCnt(reqVO);
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "유효하지 않은 API Key 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
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
				logService.insertLog(reqDTO, returnMap);
				return returnMap;
			} else {
				minfo = minfoRepository.readMinfo(minfo);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 INFO 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		Map<String,Object> payload = new HashMap<>();
		{
			payload.clear();
			payload.put("iseq", reqDTO.getSeq());
			payload.put("idx", reqDTO.getIdx());
		}
		mdataRepository.deleteMdata(payload);
		
		// 데이터 구성
		{
			returnMap.put("result", 1);
			returnMap.put("message", "성공");
		}
		logService.insertLog(reqDTO, returnMap);
		
		return returnMap;
	}
	
	public Map<String, Object> deleteFrameData(RequestDTO reqDTO) {
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0 || reqDTO.getIdx() == null) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "잘못된 파라미터 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// Request 요청 검사
		RequestVO reqVO = new RequestVO();
		reqVO.setMseq(reqDTO.getSeq());
		reqVO.setType(reqDTO.getType());
				
		if(!reqRepository.checkReq(reqVO)) {
			ModooFrame mfvo = new ModooFrame();
			mfvo.setFseq(reqDTO.getSeq());
				
			mfvo = mframeRepository.readMframe(mfvo);
			if(mfvo != null) {
				reqVO.setTitle(mfvo.getTitle());
				reqRepository.createReq(reqVO);
			}
		} else {
			reqRepository.increaseCnt(reqVO);
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "유효하지 않은 API Key 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
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
				logService.insertLog(reqDTO, returnMap);
				return returnMap;
			} else {
				mframe = mframeRepository.readMframe(mframe);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 FRAME 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);	
			return returnMap;
		}
		
		// 데이터 삭제
		List<ModooFHI> fhiList = mframeRepository.readFHI(mframe);
		Map<String, Object> payload = new HashMap<>();
		for(ModooFHI fhi : fhiList) {
			{
				payload.clear();
				payload.put("iseq", fhi.getIseq());
				payload.put("idx", reqDTO.getIdx());
			}
			mdataRepository.deleteMdata(payload);
		}
		
		// 데이터 구성
		{
			returnMap.put("result", 1);
			returnMap.put("message", "성공");
		}
		logService.insertLog(reqDTO, returnMap);
		
		return returnMap;
	}
	
	public Map<String, Object> updateFrameData(RequestDTO reqDTO) {
		Map<String, Object> returnMap = new HashMap<>();
		
		// 파라미터 유효성 검사
		if(reqDTO.getApikey() == null || reqDTO.getSeq() == 0 || reqDTO.getData() == null || reqDTO.getIdx() == null) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "잘못된 파라미터 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
			return returnMap;
		}
		
		// Request 요청 검사
		RequestVO reqVO = new RequestVO();
		reqVO.setMseq(reqDTO.getSeq());
		reqVO.setType(reqDTO.getType());
				
		if(!reqRepository.checkReq(reqVO)) {
			ModooFrame mfvo = new ModooFrame();
			mfvo.setFseq(reqDTO.getSeq());
				
			mfvo = mframeRepository.readMframe(mfvo);
			if(mfvo != null) {
				reqVO.setTitle(mfvo.getTitle());
				reqRepository.createReq(reqVO);
			}
		} else {
			reqRepository.increaseCnt(reqVO);
		}
		
		// getApiKey 유효성 검사
		if(!keyRepository.checkApikey(reqDTO.getApikey())) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "유효하지 않은 API Key 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);
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
				logService.insertLog(reqDTO, returnMap);
				return returnMap;
			} else {
				mframe = mframeRepository.readMframe(mframe);
			}
		} else {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "존재하지 않는 FRAME 입니다.");
			}
			logService.insertLog(reqDTO, returnMap);	
			return returnMap;
		}
		
		// 데이터 수정
		List<ModooFHI> fhiList = mframeRepository.readFHI(mframe);
		List<String> dataList;
		// 데이터 개수 유효성 검사
		if(reqDTO.getData().size() != fhiList.size()) {
			{
				returnMap.put("result", 0);
				returnMap.put("message", "데이터 개수가 맞지 않습니다.");
			}
			logService.insertLog(reqDTO, returnMap);	
			return returnMap;
		} else {
			dataList = reqDTO.getData();
		}
		
		Map<String, Object> payload = new HashMap<>();
		int i=0;
		for(ModooFHI fhi : fhiList) {
			if(!dataList.get(i).equals("")){
				{
					payload.clear();
					payload.put("data", dataList.get(i));
					payload.put("iseq", fhi.getIseq());
					payload.put("idx", reqDTO.getIdx());
				}
				mdataRepository.updateMdata(payload);
			}
			i++;
		}
		
		// 데이터 구성
		{
			returnMap.put("result", 1);
			returnMap.put("message", "성공");
		}
		logService.insertLog(reqDTO, returnMap);
		
		return returnMap;
	}
}
