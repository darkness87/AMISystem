package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UseDayHourAllVO {

	private Date date;
	private float useAll;
	private String type;
	private List<UseDayHourAllListVO> arrayData;

}
