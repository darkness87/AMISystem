package com.cnu.ami.failure.code.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeValueVO {

	private String regionName;
	private String estateId;
	private String estateName;
	private String buildingName;
	private String dcuId;
	private String meterId;
	private String meterType; // 미터 타입 // G, EA ...
	private Date meterDate; // 발생일시
	private String codeValue; // 코드값에 따른 설명
}
