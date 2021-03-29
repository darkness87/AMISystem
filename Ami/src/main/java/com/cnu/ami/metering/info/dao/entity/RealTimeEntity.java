package com.cnu.ami.metering.info.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GAUGE_LP_SNAPSHOT")
public class RealTimeEntity {

	@Id
	@Column(name = "METER_ID")
	private String METERID; // 전력량계 아이디
	@Column(name = "MTIME")
	private long MTIME; // 발생시각
	@Column(name = "FAP")
	private int FAP; // 순방향 유효전력
	@Column(name = "LARAP")
	private int LARAP; // 순방향 지상 무효전력
	@Column(name = "LERAP")
	private int LERAP; // 순방향 지상 무효전력
	@Column(name = "AP")
	private int AP; // 순방향 피상 전력
	@Column(name = "RFAP")
	private int RFAP; // 역방향 유효전력
	@Column(name = "RLARAP")
	private int RLARAP; // 역방향 지상 무효전력
	@Column(name = "RLERAP")
	private int RLERAP; // 역방향 지상 무효전력
	@Column(name = "RAP")
	private int RAP; // 역방향 피상 전력
	@Column(name = "MTYPE")
	private String MTYPE; // 전력량계 종류
	@Column(name = "UDATE")
	private long UDATE; // 변경시각

}
