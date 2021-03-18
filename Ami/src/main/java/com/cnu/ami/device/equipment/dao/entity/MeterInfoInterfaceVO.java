package com.cnu.ami.device.equipment.dao.entity;

public interface MeterInfoInterfaceVO {

	int getRSeq(); // 지역 SEQ

	int getGSeq(); // 단지 SEQ

	int getBSeq(); // 건물 동 SEQ

	String getRName(); // 지역명

	String getGId(); // 단지 ID

	String getGName(); // 단지명

	String getBName(); // 동명

	String getHo(); // 호명

	String getDId(); // DCU ID

	String getMeter_Id(); // METER ID

	String getMac(); // MODEM MAC

}
