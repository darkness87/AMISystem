package com.cnu.ami.metering.info.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.MeterInfoEntity;
import com.cnu.ami.metering.info.dao.entity.MeterInterfaceVO;

@Repository
public interface MeterDAO extends JpaRepository<MeterInfoEntity, Long> {

	@Query(value = "SELECT T1.DID,T1.METER_ID,T1.DEVICE_NAME,T1.MAC,T1.MRD,T1.LP_PERIOD,T1.MTYPE,T3.HSEQ,T3.BSEQ,T3.GSEQ,T3.HO\r\n"
			+ "FROM (SELECT DID,METER_ID,DEVICE_NAME,MAC,MRD,LP_PERIOD,MTYPE FROM METER_INFO WHERE DID = :dcuId AND IS_DELETE='N') AS T1\r\n"
			+ "LEFT JOIN HOUSE_METER_MAP AS T2\r\n" + "ON T1.METER_ID = T2.METER_ID\r\n"
			+ "LEFT JOIN HOUSEHOLD AS T3\r\n" + "ON T2.HSEQ=T3.HSEQ\r\n" + "ORDER BY T3.HO ASC", nativeQuery = true)
	List<MeterInterfaceVO> getMeterData(@Param("dcuId") String dcuId);

}
