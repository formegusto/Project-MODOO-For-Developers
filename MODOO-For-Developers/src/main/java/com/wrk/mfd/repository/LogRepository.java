package com.wrk.mfd.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.LogVO;

@Mapper
public interface LogRepository {
	public void insertLog(LogVO vo);
	public List<LogVO> readLog(Map<String,Object> payload);
}
