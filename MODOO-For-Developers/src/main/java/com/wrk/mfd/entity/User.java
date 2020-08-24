package com.wrk.mfd.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
	private int seq;
	private String id;
	private String password;
	private String role;
}
