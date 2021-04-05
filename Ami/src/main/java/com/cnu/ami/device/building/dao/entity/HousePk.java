package com.cnu.ami.device.building.dao.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class HousePk implements Serializable {

	private static final long serialVersionUID = 8769462620254967787L;

	private int hSeq;
	private int bSeq;
	private int gSeq;

}
