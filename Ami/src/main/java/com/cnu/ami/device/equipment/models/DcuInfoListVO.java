package com.cnu.ami.device.equipment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DcuInfoListVO {

	private int buildingSeq; // 동 건물 SEQ
	private int estateSeq; // 단지 SEQ
	private String buildingName; // 동 명 
	private String estateId; // 단지ID
	private String estateName; // 단지명
	private int meterCount; // 미터수
	private int modemCount; // 모뎀수
	private String regionName; // 지역명
	private String dcuId; // DCU ID
	private String dcuIp; // DCU IP
	private String fepIp; // FEP ID
	private String firmwareVersion; // 펌웨어 버전
	private String systemState; // 시스템 상태

}
