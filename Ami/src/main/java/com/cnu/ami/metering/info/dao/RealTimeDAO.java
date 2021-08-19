package com.cnu.ami.metering.info.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.info.dao.entity.RealTimeDcuInterfaceVO;
import com.cnu.ami.metering.info.dao.entity.RealTimeEntity;
import com.cnu.ami.metering.info.dao.entity.RealTimeInterfaceVO;

@Repository
public interface RealTimeDAO extends JpaRepository<RealTimeEntity, Long> {

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T4.HO,T1.DID,T3.METER_ID,T3.MAC,IFNULL(T5.MTIME,0) AS MTIME,IFNULL(T5.FAP,0) AS FAP,IFNULL(T5.RFAP,0) AS RFAP,T5.MTYPE,IFNULL(T5.UDATE,0) AS UDATE\r\n"
			+ "FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n" + "RIGHT JOIN BUILDING_DCU_MAP AS B\r\n"
			+ "ON A.BSEQ=B.BSEQ\r\n" + "WHERE A.GSEQ=:gseq) T1\r\n" + "LEFT JOIN\r\n"
			+ "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + "LEFT JOIN REGION AS B\r\n"
			+ "ON A.RSEQ=B.RSEQ\r\n" + "WHERE A.GSEQ=:gseq) T2\r\n" + "ON T1.GSEQ=T2.GSEQ\r\n"
			+ "LEFT JOIN METER_INFO AS T3\r\n" + "ON T1.DID=T3.DID\r\n"
			+ "LEFT JOIN (SELECT A.GSEQ,A.BSEQ,A.HSEQ,A.HO,B.METER_ID FROM HOUSEHOLD AS A\r\n"
			+ "LEFT JOIN HOUSE_METER_MAP AS B\r\n" + "ON A.HSEQ=B.HSEQ\r\n" + "WHERE A.GSEQ=:gseq) AS T4\r\n"
			+ "ON T3.METER_ID=T4.METER_ID\r\n" + "LEFT JOIN GAUGE_LP_SNAPSHOT AS T5\r\n"
			+ "ON T3.METER_ID=T5.METER_ID\r\n" + "ORDER BY T1.BNAME ASC, T1.DID ASC, T4.HO ASC", nativeQuery = true)
	List<RealTimeInterfaceVO> getRealTimeData(@Param("gseq") int gseq);

	RealTimeEntity findByMETERID(String meterid);
	
	@Query(value = "SELECT T1.METER_ID,T1.MAC,IFNULL(T2.FAP,0) AS FAP,IFNULL(T2.RFAP,0) AS RFAP,IFNULL(T2.MTIME,0) AS MTIME,T4.HO,T1.MTYPE_NAME\r\n" + 
			"FROM (SELECT METER_ID,MAC,MTYPE_NAME FROM (SELECT METER_ID,MAC,MTYPE FROM METER_INFO WHERE DID = :dcuId AND IS_DELETE = 'N') A\r\n" + 
			"LEFT JOIN METER_TYPE B ON A.MTYPE = B.MTYPE) AS T1\r\n" + 
			"LEFT JOIN (SELECT METER_ID,FAP,RFAP,MTIME FROM GAUGE_LP_SNAPSHOT) AS T2\r\n" + 
			"ON T1.METER_ID=T2.METER_ID\r\n" + 
			"LEFT JOIN (SELECT HSEQ,METER_ID FROM HOUSE_METER_MAP) AS T3\r\n" + 
			"ON T1.METER_ID=T3.METER_ID\r\n" + 
			"LEFT JOIN (SELECT HSEQ,HO FROM HOUSEHOLD) AS T4\r\n" + 
			"ON T3.HSEQ=T4.HSEQ", nativeQuery = true)
	List<RealTimeDcuInterfaceVO> getRealTimeDCUData(@Param("dcuId") String dcuId);

}
