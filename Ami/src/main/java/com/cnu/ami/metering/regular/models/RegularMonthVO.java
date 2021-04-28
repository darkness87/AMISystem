package com.cnu.ami.metering.regular.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegularMonthVO {

	private String regionName;
	private String estateId;
	private String estateName;
	private String buildingName;
	private String houseName;
	private String dcuId;
	private String meterId;
	private String mac;

	private int estateReadingDay; // 단지 정기검침일
	private int meterReadingDay; // 계량기 정기검침일

	private boolean readingDayCompare; // 정기검침일 비교

	private Date to_meterTime; // 전월 미터 측정 날짜
	private int to_apt1; // 전월 정방향 유효전력량 Total
	private int to_rapt1; // 전월 역방향 유효전력량 Total

	private Date from_meterTime; // 현월 미터 측정 날짜
	private int from_apt1; // 현월 정방향 유효전력량 Total
	private int from_rapt1; // 현월 역방향 유효전력량 Total

	private int use; // 사용량 = (from_apt1 - to_apt1) - (from_rapt1 - to_rapt1)
	private int readingStatus; // 검침 상태 코드, 0:정상, 1:오류
	
}
