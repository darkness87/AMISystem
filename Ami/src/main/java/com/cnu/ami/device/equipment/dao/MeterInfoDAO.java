package com.cnu.ami.device.equipment.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.EstateMeterInfoInterfaceVO;
import com.cnu.ami.device.equipment.dao.entity.MeterInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.MeterInfoInterfaceVO;
import com.cnu.ami.failure.code.dao.entity.MeterTypeInterfaceVO;

@Repository
public interface MeterInfoDAO extends JpaRepository<MeterInfoEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T4.HO,T1.DID,T3.METER_ID,T3.MAC,T3.MRD,T3.UDATE FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n"
			+ "RIGHT JOIN BUILDING_DCU_MAP AS B\r\n" + "ON A.BSEQ=B.BSEQ\r\n" + ") T1\r\n" + "LEFT JOIN\r\n"
			+ "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + "LEFT JOIN REGION AS B\r\n"
			+ "ON A.RSEQ=B.RSEQ\r\n" + ") T2\r\n" + "ON T1.GSEQ=T2.GSEQ\r\n" + "LEFT JOIN (SELECT * FROM METER_INFO WHERE IS_DELETE ='N') AS T3\r\n"
			+ "ON T1.DID=T3.DID\r\n" + "LEFT JOIN (SELECT A.GSEQ,A.BSEQ,A.HSEQ,A.HO,B.METER_ID FROM HOUSEHOLD AS A\r\n"
			+ "LEFT JOIN HOUSE_METER_MAP AS B\r\n" + "ON A.HSEQ=B.HSEQ\r\n" + ") AS T4\r\n"
			+ "ON T3.METER_ID=T4.METER_ID\r\n" + "ORDER BY T1.BNAME ASC, T1.DID ASC, T4.HO ASC", nativeQuery = true)
	List<MeterInfoInterfaceVO> getMeterList();

	@Query(value = "SELECT 0 AS RSEQ,0 AS GSEQ,0 AS BSEQ,T1.DID,T1.MAC,T1.METER_ID,T1.MRD,T1.UDATE FROM\r\n" + "(SELECT * FROM METER_INFO WHERE IS_DELETE ='N') AS T1\r\n"
			+ "LEFT JOIN HOUSE_METER_MAP AS T2\r\n" + "ON T1.METER_ID=T2.METER_ID\r\n" + "WHERE T2.HSEQ IS NULL\r\n"
			+ "ORDER BY T1.DID ASC, T1.MAC ASC, T1.METER_ID;", nativeQuery = true)
	List<MeterInfoInterfaceVO> getMeterNoMappList();

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T4.HO,T1.DID,T3.METER_ID,T3.MAC,T3.MRD,T3.UDATE FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n"
			+ "RIGHT JOIN BUILDING_DCU_MAP AS B\r\n" + "ON A.BSEQ=B.BSEQ\r\n" + "WHERE A.GSEQ=:gseq) T1\r\n"
			+ "LEFT JOIN\r\n" + "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n"
			+ "LEFT JOIN REGION AS B\r\n" + "ON A.RSEQ=B.RSEQ\r\n" + "WHERE A.GSEQ=:gseq) T2\r\n"
			+ "ON T1.GSEQ=T2.GSEQ\r\n" + "LEFT JOIN (SELECT * FROM METER_INFO WHERE IS_DELETE ='N') AS T3\r\n" + "ON T1.DID=T3.DID\r\n"
			+ "LEFT JOIN (SELECT A.GSEQ,A.BSEQ,A.HSEQ,A.HO,B.METER_ID FROM HOUSEHOLD AS A\r\n"
			+ "LEFT JOIN HOUSE_METER_MAP AS B\r\n" + "ON A.HSEQ=B.HSEQ\r\n" + "WHERE A.GSEQ=:gseq) AS T4\r\n"
			+ "ON T3.METER_ID=T4.METER_ID\r\n" + "ORDER BY T1.BNAME ASC, T1.DID ASC, T4.HO ASC", nativeQuery = true)
	List<MeterInfoInterfaceVO> getMeterList(@Param("gseq") int gseq);

	MeterInfoEntity findByMETERID(String meterId);

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T1.DID,T3.METER_ID,T4.MTYPE_NAME\r\n"
			+ "FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n" + "JOIN BUILDING_DCU_MAP AS B\r\n"
			+ "ON A.BSEQ=B.BSEQ) T1\r\n" + "JOIN\r\n"
			+ "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + "JOIN REGION AS B\r\n"
			+ "ON A.RSEQ=B.RSEQ) T2\r\n" + "ON T1.GSEQ=T2.GSEQ\r\n" + "JOIN METER_INFO AS T3\r\n"
			+ "ON T1.DID=T3.DID\r\n" + "JOIN METER_TYPE AS T4\r\n" + "ON T3.MTYPE=T4.MTYPE", nativeQuery = true)
	List<MeterTypeInterfaceVO> getMeterType();

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T1.DID,T3.METER_ID,T4.MTYPE_NAME\r\n"
			+ "FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n" + "JOIN BUILDING_DCU_MAP AS B\r\n"
			+ "ON A.BSEQ=B.BSEQ\r\n" + "WHERE A.GSEQ=:gseq) T1\r\n" + "JOIN\r\n"
			+ "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + "JOIN REGION AS B\r\n"
			+ "ON A.RSEQ=B.RSEQ\r\n" + "WHERE A.GSEQ=:gseq) T2\r\n" + "ON T1.GSEQ=T2.GSEQ\r\n"
			+ "JOIN METER_INFO AS T3\r\n" + "ON T1.DID=T3.DID\r\n" + "JOIN METER_TYPE AS T4\r\n"
			+ "ON T3.MTYPE=T4.MTYPE", nativeQuery = true)
	List<MeterTypeInterfaceVO> getMeterType(@Param("gseq") int gseq);

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T1.DID,T3.METER_ID,T4.MTYPE_NAME\r\n"
			+ "FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n" + "JOIN BUILDING_DCU_MAP AS B\r\n"
			+ "ON A.BSEQ=B.BSEQ\r\n" + "WHERE A.GSEQ=:gseq AND DID = :dcuId) T1\r\n" + "JOIN\r\n"
			+ "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + "JOIN REGION AS B\r\n"
			+ "ON A.RSEQ=B.RSEQ\r\n" + "WHERE A.GSEQ=:gseq) T2\r\n" + "ON T1.GSEQ=T2.GSEQ\r\n"
			+ "JOIN METER_INFO AS T3\r\n" + "ON T1.DID=T3.DID\r\n" + "JOIN METER_TYPE AS T4\r\n"
			+ "ON T3.MTYPE=T4.MTYPE", nativeQuery = true)
	List<MeterTypeInterfaceVO> getMeterType(@Param("gseq") int gseq, @Param("dcuId") String dcuId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE METER_INFO SET IS_DELETE='Y' WHERE METER_ID = :meterid", nativeQuery = true)
	public void setMeterDelete(@Param("meterid") String meterid);

	@Query(value = "SELECT S1.RSEQ,S1.GSEQ,S2.BSEQ,S1.RNAME,S1.GID,S1.GNAME,S2.BNAME,S2.HO \r\n"
			+ "FROM (SELECT G1.GSEQ,G1.RSEQ,G1.GID,G1.GNAME,G2.RNAME \r\n"
			+ "FROM (SELECT GSEQ,RSEQ,GID,GNAME FROM GROUPSET WHERE GSEQ=:gseq) AS G1\r\n" + "JOIN REGION AS G2\r\n"
			+ "ON G1.RSEQ=G2.RSEQ) AS S1\r\n" + "JOIN\r\n"
			+ "(SELECT T2.BSEQ,T2.GSEQ,T3.BNAME,T2.HO FROM(SELECT HSEQ FROM HOUSE_METER_MAP WHERE METER_ID = :meterId) AS T1\r\n"
			+ "JOIN HOUSEHOLD AS T2\r\n" + "ON T1.HSEQ=T2.HSEQ\r\n" + "JOIN BUILDING AS T3\r\n"
			+ "ON T2.BSEQ=T3.BSEQ) AS S2\r\n" + "ON S1.GSEQ=S2.GSEQ", nativeQuery = true)
	EstateMeterInfoInterfaceVO getEstateMeterInfo(@Param("gseq") int gseq, @Param("meterId") String meterId);

}
