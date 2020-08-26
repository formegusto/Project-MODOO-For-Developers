package com.wrk.mfd.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Key {
	private int seq;
	private int user_id;
	private String apikey;
}
