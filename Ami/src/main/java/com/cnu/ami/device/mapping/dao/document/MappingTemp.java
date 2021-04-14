package com.cnu.ami.device.mapping.dao.document;

import java.util.List;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MappingTemp {

	@Id
	private String _id;

	private int estateSeq;

	private String date;

	private int updateCount;

	private boolean result;

	private List<ListMappTemp> listMapp;

}
