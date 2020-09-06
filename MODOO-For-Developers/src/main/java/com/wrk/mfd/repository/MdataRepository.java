package com.wrk.mfd.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.ModooData;

@Mapper
public interface MdataRepository {
	public List<String> readMdata(ModooData vo);
	public void copyMdata(Map<String, Object> payload);
	public void postMdata(ModooData vo);
	public void updateMdata(Map<String, Object> payload);
	public void deleteMdata(Map<String, Object> payload);
}
