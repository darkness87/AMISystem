package com.cnu.ami.device.equipment.dao.entity;

public interface DcuInfoDetailInterfaceVO {

	String getDID();

	String getFEP_IP();

	int getFEP_PORT();

	String getDCU_IP();

	int getDCU_PORT();

	String getMAC_A();

	String getMAC_B();

	String getMAC_C();

	int getITIME();

	String getFWV();

	int getWAN_CODE();

	int getCOMM_CODE();

	String getT_MASK();

	int getSM_P();

	int getSMLP_P();

	int getEMLP_P();

	int getGMLP_P();

	int getEAMLP_P();

	int getGM_AVE_VA_P();

	int getGM_INST_VA_P();

	int getEAM_AVE_VA_P();

	int getEAM_INST_VA_P();

	String getF_BUILD();

	int getP_LENGTH();

	int getTIME_OUT();

	int getTRAP_INTERVAL();

	int getE_MTIME_ERROR_LIMIT();

	int getG_MTIME_ERROR_LIMIT();

	int getEA_MTIME_ERROR_LIMIT();

	int getEM_TIME_P();

	int getGM_TIME_P();

	int getEAM_TIME_P();

	int getCPU_RESET();

	String getPNID();

	String getACODE_RO();

	String getACODE_RW();

	String getSNMP_PW_RO();

	String getSNMP_PW_RW();

	String getS_SYS_DESCR();

	String getS_SYS_NAME();

	String getS_DCU_ID();

	String getS_SYS_UP_TIME();

	String getS_SYS_SERIAL();

	String getS_SYS_MODEL();

	String getS_SYS_OBJECT_ID();

	String getS_SYS_MAC();

	String getS_SYS_OS_VERS();

	String getS_SYS_HW_VERS();

	String getS_SYS_FW_VERS();

	String getS_SYS_MIB_VERS();

	String getS_SYS_DOWN_TIME();

	String getS_SYS_CON_MAC();

	int getS_SYS_MIB_ENCRYPT();

	String getS_SYS_MR_AGENT_UP_TIME();

	String getS_SYS_SNMP_AGENT_UP_TIME();

	int getS_WAN_CODE();

	int getS_COMM_CODE();

	int getS_DCU_TYPE();

	int getS_SYS_STATE();

	int getS_SYS_UP_BPS();

	int getS_SYS_DN_BPS();

	int getS_SYS_CPU_USAGE();

	int getS_SYS_MEM_USAGE();

	int getS_SYS_TEMP_VALUE();

	int getS_DCU_COVER_STATUS();

	int getS_SYS_SECURITY_STATUS();

}
