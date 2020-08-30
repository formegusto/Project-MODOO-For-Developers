package com.wrk.mfd.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.ModooBHD;
import com.wrk.mfd.entity.ModooBoard;

@Mapper
public interface MboardRepository {
	public ModooBoard readTitle(int bseq);
	public List<ModooBHD> readBHD(int bseq);
}
