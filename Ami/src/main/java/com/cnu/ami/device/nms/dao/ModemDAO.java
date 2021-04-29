package com.cnu.ami.device.nms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cnu.ami.device.equipment.dao.entity.ModemInfoEntity;
import com.cnu.ami.device.nms.dao.entity.ModemTreeInterfaceVO;

public interface ModemDAO extends JpaRepository<ModemInfoEntity, String>{

	@Query(value = "SELECT D.MAC_A AS MASTER_MODEM_MAC, I1.MAC AS MAC_STEP1, I1.ETP AS METER_STEP1, I2.MAC AS MAC_STEP2, I2.ETP AS METER_STEP2, I3.MAC AS MAC_STEP3\r\n" + 
			"	, I3.ETP AS METER_STEP3, I4.MAC AS MAC_STEP4, I4.ETP AS METER_STEP4, I5.MAC AS MAC_STEP5, I5.ETP AS METER_STEP5, I1.REGI_STAT, I1.HWV_H, I1.APMV_H\r\n" + 
			"FROM DCU_INFO AS D\r\n" + 
			"LEFT JOIN MODEM_INTERFACE AS I1\r\n" + 
			"	ON I1.PSID = D.MAC_A\r\n" + 
			"LEFT JOIN MODEM_INTERFACE AS I2\r\n" + 
			"	ON I2.PSID = I1.MAC\r\n" + 
			"LEFT JOIN MODEM_INTERFACE AS I3\r\n" + 
			"	ON I3.PSID = I2.MAC\r\n" + 
			"LEFT JOIN MODEM_INTERFACE AS I4\r\n" + 
			"	ON I4.PSID = I3.MAC\r\n" + 
			"LEFT JOIN MODEM_INTERFACE AS I5\r\n" + 
			"	ON I5.PSID = I4.MAC\r\n" + 
			"WHERE D.DID = :dcuId ", nativeQuery = true)
	public List<ModemTreeInterfaceVO> getModemMeterTree(@Param("dcuId") String dcuId);

}
