package com.wrk.mfd.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.ModooFHI;
import com.wrk.mfd.entity.ModooFrame;

@Mapper
public interface MframeRepository {
	public ModooFrame readMframe(ModooFrame vo);
	public Boolean checkSeq(ModooFrame vo);
	public List<ModooFHI> readFHI(ModooFrame vo);
}
