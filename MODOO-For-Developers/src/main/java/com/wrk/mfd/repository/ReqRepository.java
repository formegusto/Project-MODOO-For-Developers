package com.wrk.mfd.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.RequestVO;

@Mapper
public interface ReqRepository {
	public Boolean checkReq(RequestVO vo);
	public void createReq(RequestVO vo);
	public void increaseCnt(RequestVO vo);
	public List<RequestVO> readReq(Map<String, Object> payload);
	public List<RequestVO> readMinfo(String id);
	public List<RequestVO> readMframe(String id);
}
