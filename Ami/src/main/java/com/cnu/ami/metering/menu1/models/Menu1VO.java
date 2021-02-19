package com.cnu.ami.metering.menu1.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "meteringMenu1VO")
@Table(name = "TEST_LP_DATA")
public class Menu1VO {

	@Id
	@GeneratedValue // Auto IncId Generator
	@Column(name = "id")
	private long id;

	@Column(name = "meterid")
	private String meterid;

	@Column(name = "date")
	private Date date;

	@Column(name = "lp")
	private float lp;

	@Column(name = "info")
	private String info;

}
