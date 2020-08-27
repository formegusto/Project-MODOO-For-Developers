package com.wrk.mfd.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RequestVO {
	private int seq;
	private String title;
	private String type;
	private int mseq;
	private int cnt;
}
