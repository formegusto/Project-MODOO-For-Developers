package com.wrk.mfd.entity;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ModooFrame {
	private int fseq;
	private String title;
	private String id;
	private Date regdate;
}
