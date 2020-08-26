package com.wrk.mfd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wrk.mfd.entity.ModooUser;
import com.wrk.mfd.repository.MuserRepository;

@Service
public class MuserService {
	@Autowired
	private MuserRepository muserRepository;
	
	public ModooUser authMuser(ModooUser muser) {
		return muserRepository.authMuser(muser);
	}
}
