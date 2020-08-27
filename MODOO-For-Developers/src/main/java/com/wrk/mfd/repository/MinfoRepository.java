package com.wrk.mfd.repository;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.ModooInfo;

@Mapper
public interface MinfoRepository {
	public ModooInfo readMinfo(ModooInfo vo);
	public Boolean checkSeq(ModooInfo vo);
}
