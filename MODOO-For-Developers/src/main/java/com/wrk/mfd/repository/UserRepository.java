package com.wrk.mfd.repository;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.User;

@Mapper
public interface UserRepository {
	public User signinUser(User vo);
	public void signupUser(User vo);
}
