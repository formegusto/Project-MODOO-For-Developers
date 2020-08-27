package com.wrk.mfd.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.ModooData;

@Mapper
public interface MdataRepository {
	public List<String> readMdata(ModooData vo);
}
