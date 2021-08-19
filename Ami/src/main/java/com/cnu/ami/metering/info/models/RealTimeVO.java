package com.cnu.ami.metering.info.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealTimeVO {

	private int regionSeq; // 지역 SEQ 번호
	private int estateSeq; // 단지 SEQ 번호
	private int buildingSeq; // 건물 동 SEQ 번호
	private String regionName; // 지역명
	private String estateId; // 단지ID
	private String estateName; // 단지명
	private String buildingName; // 건물 동 명
	private String houseName; // 세대명
	private String dcuId; // DCU 아이디
	private String meterId; // 전력량계 아이디
	private String mac; // 모뎀 MAC번호
	private String meterTime; // 발생시각
	private int fap; // 순방향 유효전력
	private int rfap; // 역방향 유효전력
	private String updateDate; // 변경시각

}
