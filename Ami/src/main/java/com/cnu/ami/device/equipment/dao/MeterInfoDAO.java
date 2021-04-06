package com.cnu.ami.device.equipment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.MeterInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.MeterInfoInterfaceVO;
import com.cnu.ami.failure.code.dao.entity.MeterTypeInterfaceVO;

@Repository
public interface MeterInfoDAO extends JpaRepository<MeterInfoEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T4.HO,T1.DID,T3.METER_ID,T3.MAC FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n"
			+ "RIGHT JOIN BUILDING_DCU_MAP AS B\r\n" + "ON A.BSEQ=B.BSEQ\r\n" + ") T1\r\n" + "LEFT JOIN\r\n"
			+ "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + "LEFT JOIN REGION AS B\r\n"
			+ "ON A.RSEQ=B.RSEQ\r\n" + ") T2\r\n" + "ON T1.GSEQ=T2.GSEQ\r\n" + "LEFT JOIN METER_INFO AS T3\r\n"
			+ "ON T1.DID=T3.DID\r\n" + "LEFT JOIN (SELECT A.GSEQ,A.BSEQ,A.HSEQ,A.HO,B.METER_ID FROM HOUSEHOLD AS A\r\n"
			+ "LEFT JOIN HOUSE_METER_MAP AS B\r\n" + "ON A.HSEQ=B.HSEQ\r\n" + ") AS T4\r\n"
			+ "ON T3.METER_ID=T4.METER_ID\r\n" + "ORDER BY T1.BNAME ASC, T1.DID ASC, T4.HO ASC", nativeQuery = true)
	List<MeterInfoInterfaceVO> getMeterList();

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T4.HO,T1.DID,T3.METER_ID,T3.MAC FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n"
			+ "RIGHT JOIN BUILDING_DCU_MAP AS B\r\n" + "ON A.BSEQ=B.BSEQ\r\n" + "WHERE A.GSEQ=:gseq) T1\r\n"
			+ "LEFT JOIN\r\n" + "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n"
			+ "LEFT JOIN REGION AS B\r\n" + "ON A.RSEQ=B.RSEQ\r\n" + "WHERE A.GSEQ=:gseq) T2\r\n"
			+ "ON T1.GSEQ=T2.GSEQ\r\n" + "LEFT JOIN METER_INFO AS T3\r\n" + "ON T1.DID=T3.DID\r\n"
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
			+ "ON A.RSEQ=B.RSEQ\r\n" + "WHERE A.GSEQ=:gseq) T2\r\n" + "ON T1.GSEQ=T2.GSEQ\r\n" + "JOIN METER_INFO AS T3\r\n"
			+ "ON T1.DID=T3.DID\r\n" + "JOIN METER_TYPE AS T4\r\n" + "ON T3.MTYPE=T4.MTYPE", nativeQuery = true)
	List<MeterTypeInterfaceVO> getMeterType(@Param("gseq") int gseq, @Param("dcuId") String dcuId);

}
