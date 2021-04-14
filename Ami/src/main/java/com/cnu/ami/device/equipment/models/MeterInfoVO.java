package com.cnu.ami.device.equipment.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeterInfoVO {

	private int regionSeq; // 지역 SEQ
	private int estateSeq; // 단지 SEQ
	private String regionName; // 지역명
	private String estateId; // 단지 ID
	private String estateName; // 단지명
	private String buildingName; // 동명
	private String houseName; // 호명

	private String meterId; // METER ID
	private String mac; // MODEM MAC
	private String dcuId; // DCU ID
	private String deviceName; // COSEM 계기 식별자
	private int meterReadingDay; // 정기검침일
	private Date dcuTime; // DCU 시각
	private Date meterTime; // 전력량계 시각
	private int lpPeriod; // LP 주기 (1~60분)
	private int acon; // 전력량계 유효전력량 계기정수, 실제 정수 * 100을 저장
	private int rcon; // 전력량계 무효전력량 계기정수, 실제 정수 * 100을 저장
	private int pcon; // 전력량계 피상전력량 계기정수(G-type/Ea-type계기), 실제 정수 * 100을 저장
	private int netMetering; // 양방향 계량 수행 여부(G-type/Ea-type계기), 0x00: 단방향 계량, 0x01: 양방향 계량
	private String mComp; //
	private String mtype; //
	private String isDelete; // 검침대상 전력량계 삭제 여부
	private Date writeDate; // 등록일시
	private Date updateDate; // 수정일시

}
