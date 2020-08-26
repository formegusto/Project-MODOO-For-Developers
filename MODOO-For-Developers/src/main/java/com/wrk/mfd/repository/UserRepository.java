package com.wrk.mfd.repository;

import org.apache.ibatis.annotations.Mapper;

import com.wrk.mfd.entity.User;

@Mapper
public interface UserRepository {
	public User readUser(User vo);
	public User findUserById(String username);
	public void signupUser(User vo);
	public void authRegister(User vo);
}
