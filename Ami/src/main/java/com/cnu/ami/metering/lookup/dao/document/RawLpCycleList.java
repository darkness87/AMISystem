package com.cnu.ami.metering.lookup.dao.document;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawLpCycleList {

	public Date mtime;
	public int fap; // 누적유효량
	public int rfap; // 역방향 누적유효량
	public String mstr;
	public String status;

}
