package com.cnu.ami.metering.info.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectDcuVO {

	private int regionSeq;
	private int estateSeq;
	private int buildingSeq;
	private String regionName;
	private String estateId;
	private String estateName;
	private String buildingName;
	private String dcuId;

}
