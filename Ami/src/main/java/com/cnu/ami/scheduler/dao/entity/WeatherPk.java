package com.cnu.ami.scheduler.dao.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class WeatherPk implements Serializable {
	private static final long serialVersionUID = -1662347560528354547L;

	private long RSEQ;
	private String FCSTDATE;
	private String FCSTTIME;

}
