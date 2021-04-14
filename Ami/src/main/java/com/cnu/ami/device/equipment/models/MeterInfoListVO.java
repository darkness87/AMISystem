package com.cnu.ami.device.equipment.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeterInfoListVO {

	private int regionSeq; // 지역 SEQ
	private int estateSeq; // 단지 SEQ
	private int buildingSeq; // 건물 동 SEQ
	private String regionName; // 지역명
	private String estateId; // 단지 ID
	private String estateName; // 단지명
	private String buildingName; // 동명
	private String houseName; // 호명
	private String dcuId; // DCU ID
	private String meterId; // METER ID
	private String mac; // MODEM MAC
	private int readingDay; // 검침일;
	private Date updateDate; // 변경일시

}
