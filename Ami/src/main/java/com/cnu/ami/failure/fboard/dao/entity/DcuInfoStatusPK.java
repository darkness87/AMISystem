package com.cnu.ami.failure.fboard.dao.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class DcuInfoStatusPK implements Serializable {

	private static final long serialVersionUID = -1768223484741985491L;

	private String ymd;
	private String h;
	private String dstatus;

}
