package com.cnu.ami.failure.reading.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.failure.reading.dao.entity.LpSnapCountInterfaceVO;
import com.cnu.ami.metering.info.dao.entity.RealTimeEntity;
import com.cnu.ami.metering.info.dao.entity.RealTimeInterfaceVO;

@Repository
public interface FailureReadingDAO extends JpaRepository<RealTimeEntity, String>{

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T4.HO,T1.DID,T3.METER_ID,T3.MAC,T5.MTIME,T5.FAP,T5.RFAP,T5.MTYPE,T5.UDATE\r\n" + 
			"FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A JOIN BUILDING_DCU_MAP AS B\r\n" + 
			"ON A.BSEQ=B.BSEQ) T1 JOIN\r\n" + 
			"(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A JOIN REGION AS B\r\n" + 
			"ON A.RSEQ=B.RSEQ WHERE GSEQ = :gseq) T2 ON T1.GSEQ=T2.GSEQ\r\n" + 
			"JOIN (SELECT METER_ID,DID,MAC FROM METER_INFO WHERE IS_DELETE='N') AS T3 ON T1.DID=T3.DID\r\n" + 
			"LEFT JOIN (SELECT A.GSEQ,A.BSEQ,A.HSEQ,A.HO,B.METER_ID FROM HOUSEHOLD AS A\r\n" + 
			"JOIN HOUSE_METER_MAP AS B ON A.HSEQ=B.HSEQ) AS T4\r\n" + 
			"ON T3.METER_ID=T4.METER_ID JOIN (SELECT METER_ID,MTIME,FAP,RFAP,MTYPE,UDATE FROM GAUGE_LP_SNAPSHOT WHERE MTIME < :date) AS T5\r\n" + 
			"ON T3.METER_ID=T5.METER_ID ORDER BY T1.BNAME ASC, T1.DID ASC, T4.HO ASC, T3.MAC ASC", nativeQuery = true)
	List<RealTimeInterfaceVO> getFailureReadingData(@Param("gseq") int gseq, @Param("date") long date);
	
	@Query(value = "SELECT COUNT(*) AS COUNT FROM GAUGE_LP_SNAPSHOT WHERE MTIME < :date", nativeQuery = true)
	public LpSnapCountInterfaceVO getAllCount(@Param("date") long date);
	
}
