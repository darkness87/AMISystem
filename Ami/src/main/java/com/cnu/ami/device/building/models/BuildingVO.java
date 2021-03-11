package com.cnu.ami.device.building.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingVO {

	private String location; // 단지 지역
	private String EstateId; // 단지 ID
	private String EstateName; // 단지명
	private String Dong; // 단지 동명
	private String Did; // DCU ID
	private boolean Connect; // DCU ID에 대한 데이터 연동 유무

}
