package com.cnu.ami.device.estate.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstateVO {

	private int estateSeq; // 단지 SEQ
	private int regionSeq; // 지역 SEQ
	private String estateId; // 단지 ID
	private String estateName; // 단지명
	private int houseCount; // 세대수
	private String address; // 주소
	private String telEstate; // 단지전화번호
	private String manager1; // 관리자1
	private String telManager1; // 관리자1 연락처
	private String manager2; // 관리자2
	private String telManager2; // 관리자2 연락처
	private int dcuCount; // 설치 DCU 수
	private int modemCount; // 설치 DCU 수
	private int meterCount; // 설치 METER 수
	private String checkPower; // 체크 전기
	private String checkGas; // 체크 가스
	private String checkWater; // 체크 수도
	private String checkHot; // 체크 온수
	private String checkHeating; // 체크 난방
	private int dayPower; // 검침일 전기
	private int dayGas; // 검침일 가스
	private int dayWater; // 검침일 수도
	private int dayHot; // 검침일 온수
	private int dayHeating; // 검침일 난방
	private Date writeDate; // 등록일
	private Date updateDate; // 수정일
	private int buildingRegCount; // 동 등록 수
	private int houseRegCount; // 호 등록 수

}
