package com.cnu.ami.metering.mboard.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity(name = "menu1MeterLpVO")
@Entity
@Table(name = "TEST_LP_DATA")
public class Menu1MeterLpVO {

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

	@ManyToOne(optional = false)
//	@Column(name = "meterinfo")
//	@JoinColumn(name = "meterid",insertable = false, updatable = false)
	@JoinTable(name = "JOIN_TABLE", joinColumns = @JoinColumn(name = "meterid", insertable = false, updatable = false)
//			,inverseJoinColumns = @JoinColumn(name = "meterid_id")
	)
	private Menu1MeterInfoVO meterinfo;

}
