package com.wrk.mfd.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RequestDTO {
	private String apikey;
	private int seq;
	private String type;
	private List<String> data;
	private Integer idx;
	private String method;
}