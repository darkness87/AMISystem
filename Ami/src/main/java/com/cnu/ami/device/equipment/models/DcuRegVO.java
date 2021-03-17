package com.cnu.ami.device.equipment.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DcuRegVO {

	private String dcuId; // DCU ID
	private String dcuIp; // DCU IP
	private Date regDate; // DCU 등록(설치)일자
	private float latitude; // 위도
	private float longitude; // 경도

}
