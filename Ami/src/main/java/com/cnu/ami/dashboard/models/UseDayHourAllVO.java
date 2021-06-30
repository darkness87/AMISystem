package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UseDayHourAllVO {

	private String day;
	private float todayUseAll;
	private String type;
	private List<UseDayHourAllListVO> todayData;
	private List<UseDayHourAllListVO> yesterdayData;

}
