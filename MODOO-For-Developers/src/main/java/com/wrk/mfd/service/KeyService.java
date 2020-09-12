package com.wrk.mfd.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.wrk.mfd.entity.Key;
import com.wrk.mfd.entity.LogVO;
import com.wrk.mfd.entity.ModooUser;
import com.wrk.mfd.entity.User;
import com.wrk.mfd.repository.KeyRepository;
import com.wrk.mfd.repository.LogRepository;
import com.wrk.mfd.repository.UserRepository;

@Service
public class KeyService {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LogRepository logRepository;
	@Autowired
	private KeyRepository keyRepository;
	
	public void registerAuth(UserDetails userDetails, ModooUser muser) {
		User user = userService.readUser(userDetails);
		user.setModoo_id(muser.getId());
		
		Key kvo = new Key();
		kvo.setUser_id(user.getSeq());
		
		String apikey = "";
		do {
			apikey = RandomStringUtils.random(32, true, true);
		} while(keyRepository.checkApikey(apikey));
		
		kvo.setApikey(apikey);
		
		userRepository.authRegister(user);
		keyRepository.registerKey(kvo);
		
		// Security에 바뀐 역할 적용
		User newUser = userService.readUser(userDetails);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		updatedAuthorities.add(new SimpleGrantedAuthority(newUser.getRole()));
		
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
	
	public void reIssueApiKey(UserDetails userDetails) {
		User user = userService.readUser(userDetails);
		
		Key kvo = new Key();
		kvo.setUser_id(user.getSeq());
		
		Key removeKey = keyRepository.getApikey(kvo);
		LogVO log = new LogVO();
		log.setApikey(removeKey.getApikey());
		logRepository.clearLog(log);
		
		String apikey = "";
		do {
			apikey = RandomStringUtils.random(32, true, true);
		} while(keyRepository.checkApikey(apikey));
		kvo.setApikey(apikey);
		
		keyRepository.reissueKey(kvo);
	}
	
	public Key getApikey(UserDetails userDetails) {
		User user = userService.readUser(userDetails);
		
		Key kvo = new Key();
		kvo.setUser_id(user.getSeq());
		
		return keyRepository.getApikey(kvo); 
	}
}
