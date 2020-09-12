package com.wrk.mfd.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LogVO {
	private int seq;
	private String result;
	private String message;
	private int mseq;
	private String method;
	private String apikey;
	private String type;
	private Date createAt;
}
