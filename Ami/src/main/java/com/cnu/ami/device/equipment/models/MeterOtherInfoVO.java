package com.cnu.ami.device.equipment.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeterOtherInfoVO {

	private int regionSeq; // 지역 SEQ
	private int estateSeq; // 단지 SEQ
	private String regionName; // 지역명
	private String estateId; // 단지 ID
	private String estateName; // 단지명
	private String buildingName; // 동명
	private String houseName; // 호명
	private String gatewayId; // 게이트웨이 ID
	private String meterId; // 계량기 ID
	private int readingType; // 검침 타입 // 1:전기,2:가스,3:수도,4:온수,5:난방

	private String mac;
	private String deviceName;
	private int otherReadingDay; // 계량기Other 검침일
	private int lpPeriod; // 검침주기
	private Date meterTime;
}
