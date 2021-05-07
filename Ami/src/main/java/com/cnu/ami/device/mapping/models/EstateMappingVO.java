package com.cnu.ami.device.mapping.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstateMappingVO {

	private int estateSeq; // 단지 SEQ
	private String estateId; // 단지 ID
	private String estateName; // 단지명
	private int houseCount; // 세대수
	private int readingDay; // 검침일
	private List<MappingHistroyVO> mappingHistory;
	
}
