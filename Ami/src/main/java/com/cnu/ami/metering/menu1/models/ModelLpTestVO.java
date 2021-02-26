package com.cnu.ami.metering.menu1.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelLpTestVO {

	private long id;
	private String meterid;
	private Date date;
	private float lp;
	private String name;
	private int code;

}
