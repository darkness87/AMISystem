package com.cnu.ami.device.mapping.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cnu.ami.device.building.dao.entity.HouseEntity;
import com.cnu.ami.device.mapping.dao.entity.MappingInterfaceVO;

public interface MappingDAO extends JpaRepository<HouseEntity, String> {

	@Query(value = "SELECT T1.GSEQ,T1.BSEQ,IFNULL(T6.HSEQ,0) AS HSEQ,T1.BNAME,T6.HO,T4.METER_ID,T4.MRD,T4.MAC,T3.DID,T7.MTYPE,IFNULL(T7.MTIME,0) AS MTIME\r\n" + 
			"FROM (SELECT BSEQ,BNAME,GSEQ FROM BUILDING WHERE GSEQ = :gseq) AS T1\r\n" + 
			"JOIN (SELECT BSEQ,DID FROM BUILDING_DCU_MAP) AS T2\r\n" + 
			"ON T1.BSEQ=T2.BSEQ\r\n" + 
			"JOIN (SELECT DID,S_SYS_STATE,DSTATUS FROM DCU_INFO) AS T3\r\n" + 
			"ON T2.DID=T3.DID\r\n" + 
			"JOIN (SELECT METER_ID,MAC,MRD,DID FROM METER_INFO) AS T4\r\n" + 
			"ON T3.DID=T4.DID\r\n" + 
			"LEFT JOIN (SELECT HSEQ,METER_ID FROM HOUSE_METER_MAP) AS T5\r\n" + 
			"ON T4.METER_ID=T5.METER_ID\r\n" + 
			"LEFT JOIN (SELECT HSEQ,HO FROM HOUSEHOLD WHERE GSEQ = :gseq) AS T6\r\n" + 
			"ON T5.HSEQ=T6.HSEQ\r\n" + 
			"LEFT JOIN (SELECT METER_ID,MTYPE,MTIME,FAP FROM GAUGE_LP_SNAPSHOT WHERE MTIME > :mtime) AS T7\r\n" + 
			"ON T4.METER_ID=T7.METER_ID", nativeQuery = true)
	public List<MappingInterfaceVO> getEstateMappingList(@Param("gseq") int gseq, @Param("mtime") long mtime);

}
