package com.cnu.ami.device.mapping.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MappingListVO {

	private int estateSeq;
	private int buildingSeq;
	private int houseSeq;

	private String buildingName;
	private String houseName;
	private String meterId;
	private int meterReadingDay; // 계량기 검침일
	private String mac;
	private String dcuId;

	private String meterType; // 계량기 타입
	private int code; // 연동 코드

}
