package com.cnu.ami.metering.lookup.dao.document;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpCycleTemp {

	private String mid;
	private String day;
	private String did;
	private String mac;
	private Date mtime;
	private String mstr;
	private int fap;
	private int rfap;

}
