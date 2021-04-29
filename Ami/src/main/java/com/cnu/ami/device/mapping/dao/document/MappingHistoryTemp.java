package com.cnu.ami.device.mapping.dao.document;

import java.util.Date;

import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MappingHistoryTemp {

	@Id
	private String _id;
	private Date dateTime;
	private int count;
}
