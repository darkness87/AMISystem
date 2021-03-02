package com.cnu.ami.dashboard.dao.entity;

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
	@Column
	private String DID;
	@Column
	private String FEP_IP;
	@Column
	private int FEP_PORT;
	@Column
	private String DCU_IP;
	@Column
	private int DCU_PORT;
	@Column
	private String MAC_A;
	@Column
	private String MAC_B;
	@Column
	private String MAC_C;
	@Column
	private int ITIME;
	@Column
	private String FWV;
	@Column
	private int WAN_CODE;
	@Column
	private int COMM_CODE;
	@Column
	private String T_MASK;
	@Column
	private int SM_P;
	@Column
	private int SMLP_P;
	@Column
	private int EMLP_P;
	@Column
	private int GMLP_P;
	@Column
	private int EAMLP_P;
	@Column
	private int GM_AVE_VA_P;
	@Column
	private int GM_INST_VA_P;
	@Column
	private int EAM_AVE_VA_P;
	@Column
	private int EAM_INST_VA_P;
	@Column
	private String F_BUILD;
	@Column
	private int P_LENGTH;
	@Column
	private int TIME_OUT;
	@Column
	private int TRAP_INTERVAL;
	@Column
	private int E_MTIME_ERROR_LIMIT;
	@Column
	private int G_MTIME_ERROR_LIMIT;
	@Column
	private int EA_MTIME_ERROR_LIMIT;
	@Column
	private int EM_TIME_P;
	@Column
	private int GM_TIME_P;
	@Column
	private int EAM_TIME_P;
	@Column
	private int CPU_RESET;
	@Column
	private String PNID;
	@Column
	private String ACODE_RO;
	@Column
	private String ACODE_RW;
	@Column
	private String SNMP_PW_RO;
	@Column
	private String SNMP_PW_RW;
	@Column
	private String S_SYS_DESCR;
	@Column
	private String S_SYS_NAME;
	@Column
	private String S_DCU_ID;
	@Column
	private String S_SYS_UP_TIME;
	@Column
	private String S_SYS_SERIAL;
	@Column
	private String S_SYS_MODEL;
	@Column
	private String S_SYS_OBJECT_ID;
	@Column
	private String S_SYS_MAC;
	@Column
	private String S_SYS_OS_VERS;
	@Column
	private String S_SYS_HW_VERS;
	@Column
	private String S_SYS_FW_VERS;
	@Column
	private String S_SYS_MIB_VERS;
	@Column
	private String S_SYS_DOWN_TIME;
	@Column
	private String S_SYS_CON_MAC;
	@Column
	private int S_SYS_MIB_ENCRYPT;
	@Column
	private String S_SYS_MR_AGENT_UP_TIME;
	@Column
	private String S_SYS_SNMP_AGENT_UP_TIME;
	@Column
	private int S_WAN_CODE;
	@Column
	private int S_COMM_CODE;
	@Column
	private int S_DCU_TYPE;
	@Column
	private int S_SYS_STATE;
	@Column
	private int S_SYS_UP_BPS;
	@Column
	private int S_SYS_DN_BPS;
	@Column
	private int S_SYS_CPU_USAGE;
	@Column
	private int S_SYS_MEM_USAGE;
	@Column
	private int S_SYS_TEMP_VALUE;
	@Column
	private int S_DCU_COVER_STATUS;
	@Column
	private int S_SYS_SECURITY_STATUS;

}
