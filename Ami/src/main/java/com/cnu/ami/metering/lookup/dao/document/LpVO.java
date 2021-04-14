package com.cnu.ami.metering.lookup.dao.document;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LpVO {

	public Date itime;
	public Date mtime;
	public int fap; // 누적유효량
	public int larap;
	public int lerap;
	public int ap;
	public int rfap; // 역방향 누적유효량
	public int rlarap;
	public int rlerap;
	public int rap;
	public boolean ontime;
	public String mstr;
	public String status;
	
}
