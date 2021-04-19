package com.cnu.ami.device.equipment.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "DCU_INFO")
public class DcuInfoEntity {

	@Id
	@Column(name = "DID")
	private String DID;
	@Column(name = "FEP_IP")
	private String FEP_IP;
	@Column(name = "FEP_PORT")
	private int FEP_PORT;
	@Column(name = "DCU_IP")
	private String DCU_IP;
	@Column(name = "DCU_PORT")
	private int DCU_PORT;
	@Column(name = "MAC_A")
	private String MAC_A;
	@Column(name = "MAC_B")
	private String MAC_B;
	@Column(name = "MAC_C")
	private String MAC_C;
	@Column(name = "ITIME")
	private long ITIME;
	@Column(name = "FWV")
	private String FWV;
	@Column(name = "WAN_CODE")
	private String WAN_CODE;
	@Column(name = "COMM_CODE")
	private String COMM_CODE;
	@Column(name = "T_MASK")
	private String T_MASK;
	@Column(name = "SM_P")
	private int SM_P;
	@Column(name = "SMLP_P")
	private int SMLP_P;
	@Column(name = "EMLP_P")
	private int EMLP_P;
	@Column(name = "GMLP_P")
	private int GMLP_P;
	@Column(name = "EAMLP_P")
	private int EAMLP_P;
	@Column(name = "GM_AVE_VA_P")
	private int GM_AVE_VA_P;
	@Column(name = "GM_INST_VA_P")
	private int GM_INST_VA_P;
	@Column(name = "EAM_AVE_VA_P")
	private int EAM_AVE_VA_P;
	@Column(name = "EAM_INST_VA_P")
	private int EAM_INST_VA_P;
	@Column(name = "F_BUILD")
	private String F_BUILD;
	@Column(name = "P_LENGTH")
	private int P_LENGTH;
	@Column(name = "TIME_OUT")
	private int TIME_OUT;
	@Column(name = "TRAP_INTERVAL")
	private int TRAP_INTERVAL;
	@Column(name = "E_MTIME_ERROR_LIMIT")
	private int E_MTIME_ERROR_LIMIT;
	@Column(name = "G_MTIME_ERROR_LIMIT")
	private int G_MTIME_ERROR_LIMIT;
	@Column(name = "EA_MTIME_ERROR_LIMIT")
	private int EA_MTIME_ERROR_LIMIT;
	@Column(name = "EM_TIME_P")
	private int EM_TIME_P;
	@Column(name = "GM_TIME_P")
	private int GM_TIME_P;
	@Column(name = "EAM_TIME_P")
	private int EAM_TIME_P;
	@Column(name = "CPU_RESET")
	private String CPU_RESET;
	@Column(name = "PNID")
	private String PNID;
	@Column(name = "ACODE_RO")
	private String ACODE_RO;
	@Column(name = "ACODE_RW")
	private String ACODE_RW;
	@Column(name = "SNMP_PW_RO")
	private String SNMP_PW_RO;
	@Column(name = "SNMP_PW_RW")
	private String SNMP_PW_RW;
	@Column(name = "S_SYS_DESCR")
	private String S_SYS_DESCR;
	@Column(name = "S_SYS_NAME")
	private String S_SYS_NAME;
	@Column(name = "S_DCU_ID")
	private String S_DCU_ID;
	@Column(name = "S_SYS_UP_TIME")
	private String S_SYS_UP_TIME;
	@Column(name = "S_SYS_SERIAL")
	private String S_SYS_SERIAL;
	@Column(name = "S_SYS_MODEL")
	private String S_SYS_MODEL;
	@Column(name = "S_SYS_OBJECT_ID")
	private String S_SYS_OBJECT_ID;
	@Column(name = "S_SYS_MAC")
	private String S_SYS_MAC;
	@Column(name = "S_SYS_OS_VERS")
	private String S_SYS_OS_VERS;
	@Column(name = "S_SYS_HW_VERS")
	private String S_SYS_HW_VERS;
	@Column(name = "S_SYS_FW_VERS")
	private String S_SYS_FW_VERS;
	@Column(name = "S_SYS_MIB_VERS")
	private String S_SYS_MIB_VERS;
	@Column(name = "S_SYS_DOWN_TIME")
	private String S_SYS_DOWN_TIME;
	@Column(name = "S_SYS_CON_MAC")
	private String S_SYS_CON_MAC;
	@Column(name = "S_SYS_MIB_ENCRYPT")
	private String S_SYS_MIB_ENCRYPT;
	@Column(name = "S_SYS_MR_AGENT_UP_TIME")
	private String S_SYS_MR_AGENT_UP_TIME;
	@Column(name = "S_SYS_SNMP_AGENT_UP_TIME")
	private String S_SYS_SNMP_AGENT_UP_TIME;
	@Column(name = "S_WAN_CODE")
	private String S_WAN_CODE;
	@Column(name = "S_COMM_CODE")
	private String S_COMM_CODE;
	@Column(name = "S_DCU_TYPE")
	private String S_DCU_TYPE;
	@Column(name = "S_SYS_STATE")
	private String S_SYS_STATE;
	@Column(name = "S_SYS_UP_BPS")
	private int S_SYS_UP_BPS;
	@Column(name = "S_SYS_DN_BPS")
	private int S_SYS_DN_BPS;
	@Column(name = "S_SYS_CPU_USAGE")
	private int S_SYS_CPU_USAGE;
	@Column(name = "S_SYS_MEM_USAGE")
	private int S_SYS_MEM_USAGE;
	@Column(name = "S_SYS_TEMP_VALUE")
	private int S_SYS_TEMP_VALUE;
	@Column(name = "S_DCU_COVER_STATUS")
	private String S_DCU_COVER_STATUS;
	@Column(name = "S_SYS_SECURITY_STATUS")
	private String S_SYS_SECURITY_STATUS;
	@Column(name = "S_SYS_RESET")
	private String S_SYS_RESET;
	@Column(name = "S_SYS_IP_ADDR_INFO")
	private String S_SYS_IP_ADDR_INFO;
	@Column(name = "S_SYS_TRAP_RECV_INFO")
	private String S_SYS_TRAP_RECV_INFO;
	@Column(name = "S_CPU_THRESH")
	private float S_CPU_THRESH;
	@Column(name = "S_MEM_THRESH")
	private float S_MEM_THRESH;
	@Column(name = "S_TEMP_THRESH")
	private float S_TEMP_THRESH;
	@Column(name = "S_UP_BPS_THRESH")
	private float S_UP_BPS_THRESH;
	@Column(name = "S_DOWN_BPS_THRESH")
	private float S_DOWN_BPS_THRESH;
	@Column(name = "IS_DELETE")
	private String IS_DELETE;
	@Column(name = "WDATE")
	private long WDATE;
	@Column(name = "UDATE")
	private long UDATE;
	@Column(name = "DSTATUS")
	private String DSTATUS;
	@Column(name = "LAT")
	private float LAT;
	@Column(name = "LON")
	private float LON;
	@Column(name = "ROUTER_IP")
	private String ROUTER_IP;

}
