package com.cnu.ami.device.mapping.models;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MappingVO {

	private int estateSeq;
	private String estateId;
	private String estateName;
	private Date dateTime;
	private List<MappingListVO> mappingData;

}
