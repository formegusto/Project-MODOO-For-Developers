package com.wrk.mfd.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.ModooInfo;

@Mapper
public interface MinfoRepository {
	public ModooInfo readMinfo(ModooInfo vo);
	public Boolean checkSeq(ModooInfo vo);
	public List<ModooInfo> readBoardInfo(Map<String, Object> payload);
	public void copyMinfo(Map<String, Object> payload);
}
