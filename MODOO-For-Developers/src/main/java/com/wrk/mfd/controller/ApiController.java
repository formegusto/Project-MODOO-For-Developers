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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wrk.mfd.entity.Key;
import com.wrk.mfd.entity.LogVO;
import com.wrk.mfd.entity.ModooBHD;
import com.wrk.mfd.entity.ModooBoard;
import com.wrk.mfd.entity.ModooInfo;
import com.wrk.mfd.entity.RequestVO;
import com.wrk.mfd.entity.TransferVO;
import com.wrk.mfd.entity.User;
import com.wrk.mfd.repository.LogRepository;
import com.wrk.mfd.repository.MboardRepository;
import com.wrk.mfd.repository.MdataRepository;
import com.wrk.mfd.repository.MinfoRepository;
import com.wrk.mfd.repository.ReqRepository;
import com.wrk.mfd.repository.TransferRepository;
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
	@Autowired
	private TransferRepository transferRepository;
	@Autowired
	private MboardRepository mboardRepository;
	@Autowired
	private MinfoRepository minfoRepository;
	@Autowired
	private MdataRepository mdataRepository;
	
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
		
		{
			payload.clear();
			payload.put("requser", user.getModoo_id());
			payload.put("resuser", user.getModoo_id());
			payload.put("start", 0);
			payload.put("limit", 5);
		}
		List<TransferVO> transferList = transferRepository.readTransfer(payload);
		
		model.addAttribute("transferList", transferList);
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
	
	@GetMapping("/transfer")
	public String transfer(@AuthenticationPrincipal UserDetails userDetails,
			Model model) {
		User user = userService.readUser(userDetails);
		
		Map<String, Object> payload = new HashMap<>();
		{
			payload.clear();
			payload.put("requser", user.getModoo_id());
			payload.put("limit", 5);
		}
		List<TransferVO> myReqList = transferRepository.readMyTrans(payload);
		
		{
			payload.clear();
			payload.put("resuser", user.getModoo_id());
			payload.put("limit", 5);
		}
		List<TransferVO> otherReqList = transferRepository.readOtherTrans(payload);
		
		model.addAttribute("myReqList", myReqList);
		model.addAttribute("otherReqList", otherReqList);
		model.addAttribute("user", user);
		
		return "/transfer";
	}
	
	@PostMapping("/negativeReq")
	public String negativeReq(TransferVO transfer) {
		transferRepository.negativeReq(transfer);
		
		return "redirect:/transfer";
	}
	
	@GetMapping("/accepting")
	public String accepting(@AuthenticationPrincipal UserDetails userDetails,
			int seq, Model model) {
		User user = userService.readUser(userDetails);
		TransferVO transfer = transferRepository.readTransferOne(seq);
		ModooBoard mboard = mboardRepository.readTitle(transfer.getBseq());
		List<ModooBHD> bhdList = mboardRepository.readBHD(transfer.getBseq());
		
		Map<String, Object> payload = new HashMap<>();
		for(ModooBHD bhd : bhdList) {
			if(bhd.getType().equals("frame")) {
				{
					payload.put("fseq", bhd.getSeq());
				}
			} else if(bhd.getType().equals("tm")) {
				{
					payload.put("tseq", bhd.getSeq());
				}
			} else if(bhd.getType().equals("visual")) {
				{
					payload.put("vseq", bhd.getSeq());
				}
			}
		}
		List<ModooInfo> infoList = minfoRepository.readBoardInfo(payload);
		
		model.addAttribute("seq", seq);
		model.addAttribute("requser", transfer.getRequser());
		model.addAttribute("board", mboard);
		model.addAttribute("infoList", infoList);
		model.addAttribute("user", user);
		
		return "/accepting";
	}
	
	@PostMapping("/acceptReq")
	public String acceptReq(TransferVO transfer, String iseqList) {
		String[] iseqListArr = iseqList.split(",");
		Map<String, Object> payload = new HashMap<>();
		
		for(String iseq : iseqListArr) {
			{
				payload.clear();
				payload.put("iseq", Integer.parseInt(iseq));
				payload.put("id", transfer.getRequser());
			}
			
			minfoRepository.copyMinfo(payload);
			mdataRepository.copyMdata(payload);
		}
		transferRepository.acceptReq(transfer);
		
		return "redirect:/transfer";
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
	
	@GetMapping("/manual")
	public String manul(@AuthenticationPrincipal UserDetails userDetails,
			Model model) {
		User user = userService.readUser(userDetails);

		model.addAttribute("user", user);
		return "/manual";
	}
}
