package com.cnu.ami.metering.mboard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.info.dao.entity.RealTimeEntity;
import com.cnu.ami.metering.mboard.dao.entity.MBoardCountInterfaceVO;

@Repository
public interface MBoardDAO extends JpaRepository<RealTimeEntity, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	@Query(value = "SELECT T1.RSEQ,T1.RNAME,IFNULL(T2.HOUSECOUNT,0) AS COUNT, IFNULL(T2.GCOUNT,0) AS GCOUNT\r\n" + 
			"FROM (SELECT RSEQ,RNAME FROM REGION) AS T1\r\n" + 
			"LEFT JOIN (SELECT RSEQ,SUM(CNT_HOUSE) AS HOUSECOUNT, COUNT(GSEQ) AS GCOUNT FROM GROUPSET WHERE IS_DELETE = 'N' GROUP BY RSEQ) AS T2\r\n" + 
			"ON T1.RSEQ=T2.RSEQ;", nativeQuery = true)
	List<MBoardCountInterfaceVO> getHouseCount();
	
	@Query(value = "SELECT S1.RSEQ,S1.RNAME,IFNULL(SUM(S2.DCUCOUNT),0) AS COUNT\r\n" + 
			"FROM(SELECT G1.RSEQ,G1.RNAME,G2.GSEQ,GNAME\r\n" + 
			"FROM (SELECT RSEQ,RNAME FROM REGION) AS G1\r\n" + 
			"LEFT JOIN (SELECT RSEQ,GSEQ,GNAME FROM GROUPSET WHERE IS_DELETE = 'N') AS G2\r\n" + 
			"ON G1.RSEQ=G2.RSEQ) AS S1\r\n" + 
			"LEFT JOIN\r\n" + 
			"(SELECT GSEQ,COUNT(*) AS DCUCOUNT FROM BUILDING AS T1\r\n" + 
			"JOIN BUILDING_DCU_MAP AS T2\r\n" + 
			"ON T1.BSEQ=T2.BSEQ\r\n" + 
			"JOIN (SELECT DID FROM DCU_INFO WHERE IS_DELETE = 'N' AND DSTATUS != 'RET_SUCCESS') AS T3\r\n" + 
			"ON T2.DID=T3.DID\r\n" + 
			"GROUP BY GSEQ) AS S2\r\n" + 
			"ON S1.GSEQ=S2.GSEQ\r\n" + 
			"GROUP BY RSEQ;", nativeQuery = true)
	List<MBoardCountInterfaceVO> getDcuCount();
	
	@Query(value = "SELECT M1.RSEQ,M1.RNAME,IFNULL(SUM(M2.METERCOUNT),0) AS COUNT\r\n" + 
			"FROM(SELECT G1.RSEQ,G1.RNAME,G2.GSEQ,GNAME\r\n" + 
			"FROM (SELECT RSEQ,RNAME FROM REGION) AS G1\r\n" + 
			"LEFT JOIN (SELECT RSEQ,GSEQ,GNAME FROM GROUPSET WHERE IS_DELETE = 'N') AS G2\r\n" + 
			"ON G1.RSEQ=G2.RSEQ) AS M1\r\n" + 
			"LEFT JOIN\r\n" + 
			"(SELECT GSEQ,COUNT(T4.METER_ID) AS METERCOUNT FROM BUILDING AS T1\r\n" + 
			"JOIN BUILDING_DCU_MAP AS T2\r\n" + 
			"ON T1.BSEQ=T2.BSEQ\r\n" + 
			"JOIN (SELECT DID FROM DCU_INFO WHERE IS_DELETE = 'N') AS T3\r\n" + 
			"ON T2.DID=T3.DID\r\n" + 
			"JOIN (SELECT METER_ID,DID FROM METER_INFO WHERE IS_DELETE) AS T4\r\n" + 
			"ON T3.DID=T4.DID\r\n" + 
			"JOIN (SELECT METER_ID FROM GAUGE_LP_SNAPSHOT WHERE MTIME <= :mtime) AS T5\r\n" + 
			"ON T4.METER_ID=T5.METER_ID\r\n" + 
			"GROUP BY GSEQ) AS M2\r\n" + 
			"ON M1.GSEQ=M2.GSEQ\r\n" + 
			"GROUP BY RSEQ;", nativeQuery = true)
	List<MBoardCountInterfaceVO> getMeterCount(@Param("mtime") long mtime);
	
}
