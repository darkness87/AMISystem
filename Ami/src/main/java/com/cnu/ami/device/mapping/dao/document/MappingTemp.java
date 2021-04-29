package com.cnu.ami.device.mapping.dao.document;

import java.util.Date;
import java.util.List;

import com.cnu.ami.device.mapping.models.MappingListVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MappingTemp {

	private int estateSeq;
	private String estateId;
	private String estateName;
	private Date dateTime;
	private int count;
	private List<MappingListVO> mappingData;

}
