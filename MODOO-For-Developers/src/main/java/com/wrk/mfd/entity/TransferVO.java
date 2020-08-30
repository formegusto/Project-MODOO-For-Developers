package com.wrk.mfd.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TransferVO {
	private int seq;
	private int bseq;
	private String requser;
	private String resuser;
	private String reqmsg;
	private String resmsg;
	private String status;
	private Date createAt;
}
