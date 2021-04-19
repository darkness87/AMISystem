package com.cnu.ami.device.equipment.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DcuInfoVO {

	private String regionName; // 지역명
	private String estateName; // 단지명
	private String buildingName; // 건물명
	
	private String dcuId; // DCU ID
	private String fepIp; // FEP IP
	private int fepPort; // FEP PORT
	private String dcuIp; // DCU IP
	private int dcuPort; // DCU PORT
	private String macA; // 마스터 모뎀 A 맥어드레스
	private String macB; // 마스터 모뎀 B 맥어드레스
	private String macC; // 마스터 모뎀 C 맥어드레스
	private Date dcuCurrentTime; // DCU 현재 시각
	private String firmwareVersion; // DCU FIRMWARE VERSION
	private String wanCode; // 간성망 분류 코드 (1:HFC, 2:OPTICAL, 3:LTE)
	private String commCode; // 인입망 통신기술 분류코드 (1:HS-PLC, 2:HPGP, 3:WI-SUN, 4:ZIGBEE)
	private String tMask; // DCU에서 검침FEP으로 보내는 Trap 또는 자율보고 정보관리 금지 항목값 설정 (Default : 0x00000000, 모두 허용)
	private int sTypePeriod; // 표준형 계기 현재 검침 수집 주기 조정
	private int sTypeLpPeriod; // 표준형 계기 LP 수집 주기 조정
	private int eTypelpPeriod; // E-TYPE 계기 LP 수집 주기 조정
	private int gTypelpPeriod; // G-TYPE 계기 LP 수집 주기 조정
	private int eaTypeLpPeriod; // EA-TYPE 계기 LP 수집 주기 조정
	private int gTypeAvgVoltagePeriod; // G-TYPE 계기 평균 전압/전류 수집 주기 조정
	private int gTypeInstVoltagePeriod; // G-TYPE 계기 순시 전압/전류 수집 주기 조정 
	private int eaTypeAvgVoltagePeriod; // EA-TYPE 계기 평균 전압/전류 수집 주기 조정
	private int eaTypeInstVoltagePeriod; // EA-TYPE 계기 순시 전압/전류 수집 주기 조정
	private String firmwareBuild; // Firmware 관리번호, Firmware 빌드연번 혹은 빌드일자(연월일 BCD표현)
	private int packetLength; // 검침FEP-DCU간 프로토콜 패킷 길이(bytes), 초기값 0x00의 경우, 통신망 혹은 DCU OS 기기 기본 MTU 설정치에서 TCP/IP 헤더길이를 뺀 길이 적용
	private int timeOut; // DCU-FEP간 프로토콜상 Time out 시간, 초기값 : 3초
	private int trapInterval; // DCU-FEP간 Trap 전송 실패시 재전송 Interval (단위 : 분), 초기값 : 15분
	private int etypeTimeErrorLimit; // DCU와 E-type 전력량계간 시간오차 한계(Default 0x02, 2분)
	private int gtypeTimeErrorLimit; // DCU와 G-type 전력량계간 시간오차 한계(Default 0x01, 1분)
	private int eatypeTimeErrorLimit; // DCU와 Ea-type 전력량계간 시간오차 한계(Default 0x01, 1분)
	private int eTypeTimePeriod; // E-type 전력량계 시간확인 주기
	private int gTypeTimePeriod; // G-type 전력량계 시간확인 주기 (Default 0x0F, 15분)
	private int eaTypeTimePeriod; // Ea-type 전력량계 시간확인 주기 (Default 0x0F, 15분)
	private String cpuReset; // CPU 자동 reset 기능 활성화/비활성화
	
	private String pnid; // 콘솔 무선 모듈의 PNID
	private String acodeReadOnly; // 인증권한 코드 (READ ONLY)
	private String acodeReadWrite; // 인증권한코드 (READ / WRITE)
	private String snmpReadOnly;; // SNMP COMMUNITY VALUE (READ ONLY)
	private String snmpReadWrite; // SNMP COMMUNITY VALUE (READ / WRITE)
	private String sysDcuDesc; // DCU 관련 설명, 제조사명, 제조사 유지보수 담당 연락처 필수 추가사항 자율 // ex> AAA co., 042-865-0000, asd sdsd
	private String sysDcuName; // 통신 단말 장치(DCU)명 // 값> AMI-DCU(fixed-Value)
	private String sysDcuId; // DCU ID (DCU 설치 위치- 전주번호) // 검침FEP-DCU간 프로토콜 주소와 일치 (DID/SID)
	private String sysUpTime; // DCU 동작시간 // ex> 3 day, 21Hours, 53 minutes, 4 seconds
	private String sysSerial; // DCU 고유번호 (검침FEP-DCU간 프로토콜과 일치) // VCode+YYMM+NUM (검침FEP-DCU간 프로토콜상 BCD 형태의 YYMM은 ASCII string 로 변환하여 표기) // ex> 제조사코드 : 31, 생산연월 : 15년 08월, 제조연번 : 1234567 => 3115081234567
	private String sysModel; // DCU 모델번호 // ex> M0012351556
	private String sysObjectId; // 제조사 모델 식별키 (Optional) // ex> 1.3.6.1.4.1.8072.3.2.10
	private String sysMac; // WAN용 ethernet 포트 맥 주소 // ex> 00:0c:93:71:80:81
	private String sysOsVersion; // OS 버전 // ex> Linux 2.6.18-164.11.1.el5
	private String sysHardWareVersion; // 하드웨어 버전(업체 자율 관리) // ex> HWVers.02
	private String sysFirmWareVersion; // Firmware 관리번호 : Firmware 빌드연번 혹은 빌드일자(연월일 BCD표현) // ex> 0x120412(F-build) -> 120412
	private String sysMibVersion; // MIB 정보 버전 번호 // > 2.0
	private String sysDownTime; // DCU 정전시간 // ex> 3 day, 21Hours, 53 minutes, 4 seconds
	private String sysConsoleMac; // Console용 ethernet 포트 맥 주소 // ex> 00:0c:93:71:80:81
	private String sysMibEncrypt; // SNMP 암호화 여부 // 0 : 암호화 미적용, 1 : PDU의 value값 암호화
	private String sysMrAgentUpTime; // DCU 검침 Agent 동작시간 // ex> 3 day, 21Hours, 53 minutes, 4 seconds
	private String sysSnmpAgentUpTime; // DCU SNMP Agent 동작시간 // ex> 3 day, 21Hours, 53 minutes, 4 seconds 
	private String sysWanCode; // 간선망 분류코드 // 01 : HFC, 02 : Optical, 03 : LTE
	private String sysCommCode; // 인입망 통신기술 분류코드 // 01 : HS-PLC(ISO/IEC12139-1) , 02 : HPGP , 03 : Wi-SUN , 04 : Zigbee
	private String sysDcuType; // DCU 종류 // 1 : TDU 내장된 DCU , 2 : TDU 내장되지 않은 DCU
	private String sysState; // DCU 동작상태  // 0 : 이상 , 1 : 정상
	private int sysUpBps; // DCU 평균 업로드 BPS(Bit/Sec)
	private int sysDownBps; // DCU 평균 다운로드 BPS(Bit/Sec)
	private int sysCpuUsage; // DCU CPU 사용률 (%) // 10초 주기로 수집하여 계산된 1분 평균값
	private int sysMemoryUsage; // DCU MEMORY 사용률 (%) // 10초 주기로 수집하여 계산된 1분 평균값
	private int sysTempValue; // 설비 온도 (optional) // 10초 주기로 수집하여 계산된 1분 평균값
	private String sysDcuCoverStatus; // DCU Cover 개폐상태 // 0 : OPEN , 1 : CLOSED
	private String sysSecurityStatus; // SNMP Agent 암복호화 기능 상태 // 0 : 정상 , 1 : DCU 보안키 Not Found , 2 : 암호모듈 초기화 실패, 3 : 암호모듈 연동 실패 , 4 : 암호화 실패 , 5 : 복호화 실패 , 6 : 기타(정의되지 않는 오류)
	
	private String sysReset; // DCU의 프로세스를 리셋하거나 DCU 자체를 Rebooting // 0 : Normal, 1 : All Reset (DCU의 검침 Agent와 SNMP Agent의 프로세스를 kill한 후 다시 시작함), 2 : 검침 Agent만 Reset, 3 : SNMP Agnet만 Reset, 4 : Reboot (DCU의 OS를 리부팅함)
	private String sysIpAddrInfo; // DCU 아이피 정보 // ex> 192.168.100.100
	private String sysTrapRecvInfo; // DCU SNMP Trap 수신서버 정보 // ex>192.168.100.100:162 (Manager Ip : Port)
	private float sysCpuThresh; // DCU CPU 임계치(%)
	private float sysMemoryThresh; // DCU 메모리 임계치(%)
	private float sysTempThresh; // DCU 온도 임계치(optional)
	private float sysUpBpsThresh; // 검침FEP-DCU간 업로드 BPS 임계치
	private float sysDownBpsThresh; // 검침FEP-DCU간 다운로드 BPS 임계치
	private String isDelete; // 삭제유무
	private Date writeDate; // 등록시각
	private Date updateDate; // 수정시각
	private String dcuStatus; // 'RET_SUCCESS','RET_FAIL_CONNECTION','RET_FAIL_SEND','RET_FAIL_READ','RET_FAIL_NOT_ACK'
	
	private float latitude; // 위도
	private float longitude; // 경도
	private String routerIp; // 라우터 IP

}
