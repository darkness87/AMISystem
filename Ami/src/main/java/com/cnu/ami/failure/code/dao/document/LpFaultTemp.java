package com.cnu.ami.failure.code.dao.document;

import java.util.Date;

import javax.persistence.Id;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class LpFaultTemp {

	@Id
	private String _id;
	private String mstr;
	private String did;
	private String mid;
	private String day;

	private Date itime;
	private Date mtime;

	private int f1;
	private int f2;
	private int f3;
	private int f4;
	private int f5;
	private int f6;
	private int f7;
	private int f8;
	private int f9;
	private int f10;
	private int f11;
	private int f12;
	private int f13;
	private int f14;
	private int f15;
	private int f16;
	private int f17;
	private int f18;
	private int f19;
	private int f20;
	private int f21;
	private int f22;
	private int f23;
	private int f24;

}
