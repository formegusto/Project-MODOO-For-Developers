package com.wrk.mfd.repository;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.Key;

@Mapper
public interface KeyRepository {
	public void registerKey(Key vo);
	public void reissueKey(Key vo);
	public Boolean checkApikey(String apikey);
	public Key getApikey(Key vo);
	public int readUserId(String apikey);
}
