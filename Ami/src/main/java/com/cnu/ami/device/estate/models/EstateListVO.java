package com.cnu.ami.device.estate.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstateListVO {

	private int estateSeq; // 단지 SEQ
	private int regionSeq; // 지역 SEQ
	private String regionName; // 지역명
	private String estateId; // 단지 ID
	private String estateName; // 단지명
	private int houseCount; // 세대수
	private String address; // 주소
	private int deviceCount; // 설치 장비 수
	private int readingTypeCount; // 검침 타입 수
	private Date writeDate; // 등록일
	private Date updateDate; // 수정일

}
