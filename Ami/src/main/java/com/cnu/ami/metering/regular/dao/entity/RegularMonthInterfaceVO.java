package com.cnu.ami.metering.regular.dao.entity;

public interface RegularMonthInterfaceVO {

	String getRname();

	String getGid();

	String getGname();

	String getBname();

	String getHo();

	String getDid();

	String getMeter_Id();

	int getMrd();

	String getMac();

	long getFrom_Mtime(); // 전월시간

	int getFrom_Apt1(); // 전월 유효전력량

	int getFrom_R_Apt1(); // 전월 역방향

	long getTo_Mtime(); // 현월시간

	int getTo_Apt1(); // 현월 유효전력량

	int getTo_R_Apt1(); // 현월 역방향

	int getF_Use(); // 정방향 사용량

	int getR_Use(); // 역방향 사용량

}
