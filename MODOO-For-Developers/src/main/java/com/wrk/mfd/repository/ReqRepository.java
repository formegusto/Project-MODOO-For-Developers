package com.wrk.mfd.repository;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.RequestVO;

@Mapper
public interface ReqRepository {
	public Boolean checkReq(RequestVO vo);
	public void createReq(RequestVO vo);
	public void increaseCnt(RequestVO vo);
}
