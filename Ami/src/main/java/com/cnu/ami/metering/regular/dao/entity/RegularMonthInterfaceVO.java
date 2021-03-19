package com.cnu.ami.metering.regular.dao.entity;

public interface RegularMonthInterfaceVO {

	int getRseq();
	int getGseq();
	int getBseq();

	String getRname();
	String getGid();
	String getGname();
	String getBname();
	String getHo();
	String getDid();
	String getMeter_Id();
	String getMac();

	long getItime();
	long getMtime();

	int getApt1(); // 유효전력량 Total
	int getApt2(); // 피상전력량 Total
	int getRpt(); // 지상 무효전력량 Total
	int getLpt(); // 진상 무효전력량 Total
	int getPft(); // 평균 역률 Total
	
}
