package com.wrk.mfd.entity;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ModooInfo {
	private int iseq;
	private String title;
	private String field;
	private String id;
	private Date regdate;
}
