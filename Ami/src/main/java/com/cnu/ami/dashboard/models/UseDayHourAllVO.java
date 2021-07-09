package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UseDayHourAllVO {

	private String day;
	private String unit; // 단위 : Wh, kWh 등
	private float todayUseAll;
	private String type;
	private List<UseDayHourAllListVO> todayData;
	private List<UseDayHourAllListVO> yesterdayData;

}
