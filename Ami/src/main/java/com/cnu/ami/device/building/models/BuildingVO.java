package com.cnu.ami.device.building.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingVO {

	private int buildingSeq; // 동 건물 Seq번호
	private int estateSeq; // 단지 Seq번호
	private String buildingName; // 동명
	private String estategId; // 단지ID
	private String estateName; // 단지명
	private String dcuId; // DCU ID
	private int systmeState; // 시스템 상태

}
