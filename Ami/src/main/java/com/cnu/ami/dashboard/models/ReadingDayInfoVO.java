package com.cnu.ami.dashboard.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadingDayInfoVO {

	private String readingDay; // 검침일
	private int houseCount; // 검침 수용가 수
	private int houseErrorCount; // 미검침 수용가 수
	private float readingRate; // 정기검침 성공률

}
