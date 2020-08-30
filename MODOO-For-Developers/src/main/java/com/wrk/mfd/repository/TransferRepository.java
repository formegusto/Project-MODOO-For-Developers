package com.wrk.mfd.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.TransferVO;

@Mapper
public interface TransferRepository {
	public List<TransferVO> readTransfer(Map<String,Object> payload);
	public List<TransferVO> readMyTrans(Map<String,Object> payload);
	public List<TransferVO> readOtherTrans(Map<String,Object> payload);
	public void negativeReq (TransferVO vo);
	public void acceptReq (TransferVO vo);
	public TransferVO readTransferOne(int seq);
}
