package com.cnu.ami.metering.mboard.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LP_TEST_DATA")
public class lpTestDataVO {

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "meterid")
	private String meterid;

	@Column(name = "date")
	private Date date;

	@Column(name = "lp")
	private float lp;

}
