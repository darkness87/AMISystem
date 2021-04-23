package com.cnu.ami.device.equipment.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DcuRealtimeStatusVO {

	private long sysState; // DCU 동작상태  // 0 : 이상 , 1 : 정상
	private long sysUpBps; // DCU 평균 업로드 BPS(Bit/Sec)
	private long sysDownBps; // DCU 평균 다운로드 BPS(Bit/Sec)
	private long sysCpuUsage; // DCU CPU 사용률 (%) // 10초 주기로 수집하여 계산된 1분 평균값
	private long sysMemoryUsage; // DCU MEMORY 사용률 (%) // 10초 주기로 수집하여 계산된 1분 평균값
	private long sysTempValue; // 설비 온도 (optional) // 10초 주기로 수집하여 계산된 1분 평균값
	private long sysDcuCoverStatus; // DCU Cover 개폐상태 // 0 : OPEN , 1 : CLOSED

}
