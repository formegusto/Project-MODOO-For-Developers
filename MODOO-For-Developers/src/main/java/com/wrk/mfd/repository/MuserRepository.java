package com.wrk.mfd.repository;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.ModooUser;

@Mapper
public interface MuserRepository {
	public ModooUser authMuser(ModooUser vo);
}
