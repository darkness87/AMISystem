package com.cnu.ami.metering.regular.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GAUGE_PERIOD")
public class RegularMonthEntity {

	@Id
	@Column(name = "METER_ID")
	private String METERID;
	@Column(name = "ITIME")
	private long ITIME;
	@Column(name = "MTIME")
	private long MTIME;

	@Column(name = "APT1")
	private int APT1; // 유효전력량 Total
	@Column(name = "APT2")
	private int APT2; // 피상전력량 Total
	@Column(name = "RPT")
	private int RPT; // 지상 무효전력량 Total
	@Column(name = "LPT")
	private int LPT; // 진상 무효전력량 Total
	@Column(name = "PFT")
	private int PFT; // 평균 역률 Total

	@Column(name = "APT1A")
	private int APT1A;
	@Column(name = "APT2A")
	private int APT2A;
	@Column(name = "RPTA")
	private int RPTA;
	@Column(name = "LPTA")
	private int LPTA;
	@Column(name = "PFTA")
	private int PFTA;

	@Column(name = "APT1B")
	private int APT1B;
	@Column(name = "APT2B")
	private int APT2B;
	@Column(name = "RPTB")
	private int RPTB;
	@Column(name = "LPTB")
	private int LPTB;
	@Column(name = "PFTB")
	private int PFTB;

	@Column(name = "APT1C")
	private int APT1C;
	@Column(name = "APT2C")
	private int APT2C;
	@Column(name = "RPTC")
	private int RPTC;
	@Column(name = "LPTC")
	private int LPTC;
	@Column(name = "PFTC")
	private int PFTC;

	@Column(name = "APT1D")
	private int APT1D;
	@Column(name = "APT2D")
	private int APT2D;
	@Column(name = "RPTD")
	private int RPTD;
	@Column(name = "LPTD")
	private int LPTD;
	@Column(name = "PFTD")
	private int PFTD;

	@Column(name = "R_APT1")
	private int R_APT1;
	@Column(name = "R_APT2")
	private int R_APT2;
	@Column(name = "R_RPT")
	private int R_RPT;
	@Column(name = "R_LPT")
	private int R_LPT;
	@Column(name = "R_PFT")
	private int R_PFT;

	@Column(name = "R_APT1A")
	private int R_APT1A;
	@Column(name = "R_APT2A")
	private int R_APT2A;
	@Column(name = "R_RPTA")
	private int R_RPTA;
	@Column(name = "R_LPTA")
	private int R_LPTA;
	@Column(name = "R_PFTA")
	private int R_PFTA;

	@Column(name = "R_APT1B")
	private int R_APT1B;
	@Column(name = "R_APT2B")
	private int R_APT2B;
	@Column(name = "R_RPTB")
	private int R_RPTB;
	@Column(name = "R_LPTB")
	private int R_LPTB;
	@Column(name = "R_PFTB")
	private int R_PFTB;

	@Column(name = "R_APT1C")
	private int R_APT1C;
	@Column(name = "R_APT2C")
	private int R_APT2C;
	@Column(name = "R_RPTC")
	private int R_RPTC;
	@Column(name = "R_LPTC")
	private int R_LPTC;
	@Column(name = "R_PFTC")
	private int R_PFTC;

	@Column(name = "R_APT1D")
	private int R_APT1D;
	@Column(name = "R_APT2D")
	private int R_APT2D;
	@Column(name = "R_RPTD")
	private int R_RPTD;
	@Column(name = "R_LPTD")
	private int R_LPTD;
	@Column(name = "R_PFTD")
	private int R_PFTD;

}
