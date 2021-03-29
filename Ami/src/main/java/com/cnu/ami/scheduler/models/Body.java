package com.cnu.ami.scheduler.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {

	private String dataType;
	private List<Item> items;
	private int numOfRows;
	private int pageNo;
	private int totalCount;

}
