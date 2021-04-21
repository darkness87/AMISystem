package com.cnu.ami.device.equipment.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoInterfaceVO;
import com.cnu.ami.device.equipment.dao.entity.DeviceBuildingInterfaceVO;

@Repository
public interface DcuInfoDAO extends JpaRepository<DcuInfoEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

	DcuInfoEntity findByDID(String did);

	@Query(value = "SELECT IFNULL(AA.BSEQ,0) AS BSEQ,IFNULL(AA.GSEQ,0) AS GSEQ,AA.BNAME,AA.GID,AA.GNAME,IFNULL(AA.CNT_METER,0) AS CNT_METER,IFNULL(AA.CNT_MODEM,0) AS CNT_MODEM,AA.RNAME,BB.DID,BB.DCU_IP,BB.FEP_IP,BB.FWV,BB.S_SYS_STATE\r\n"
			+ "FROM (SELECT A.BSEQ,A.GSEQ,A.BNAME,B.GID,B.GNAME,B.CNT_METER,B.CNT_MODEM,B.RNAME\r\n"
			+ "FROM BUILDING AS A\r\n"
			+ "JOIN (SELECT T1.GSEQ,T1.RSEQ,T1.GID,T1.GNAME,T1.CNT_METER,T1.CNT_MODEM,T2.RNAME FROM GROUPSET AS T1\r\n"
			+ "JOIN REGION AS T2\r\n" + "ON T1.RSEQ=T2.RSEQ\r\n" + ") AS B\r\n" + "ON A.GSEQ=B.GSEQ) AA\r\n"
			+ "RIGHT JOIN \r\n" + "(SELECT C.DID,C.DCU_IP,C.FEP_IP,C.FWV,C.S_SYS_STATE,D.BSEQ\r\n"
			+ "FROM DCU_INFO AS C\r\n" + "LEFT JOIN BUILDING_DCU_MAP AS D\r\n"
			+ "ON C.DID=D.DID WHERE C.IS_DELETE='N') BB\r\n" + "ON AA.BSEQ=BB.BSEQ", nativeQuery = true)
	public List<DcuInfoInterfaceVO> getDcuList();

	@Query(value = "SELECT 0 AS BSEQ,0 AS GSEQ,0 AS CNT_METER,0 AS CNT_MODEM,T1.DID,T1.DCU_IP,T1.FEP_IP,T1.FWV,T1.S_SYS_STATE FROM DCU_INFO AS T1\r\n"
			+ "LEFT JOIN BUILDING_DCU_MAP AS T2 ON T1.DID=T2.DID WHERE T1.IS_DELETE='N'\r\n"
			+ "AND T2.BSEQ IS NULL", nativeQuery = true)
	public List<DcuInfoInterfaceVO> getDcuNoMappList();

	@Query(value = "SELECT IFNULL(AA.BSEQ,0) AS BSEQ,IFNULL(AA.GSEQ,0) AS GSEQ,AA.BNAME,AA.GID,AA.GNAME,IFNULL(AA.CNT_METER,0) AS CNT_METER,IFNULL(AA.CNT_MODEM,0) AS CNT_MODEM,AA.RNAME,BB.DID,BB.DCU_IP,BB.FEP_IP,BB.FWV,BB.S_SYS_STATE\r\n"
			+ "FROM (SELECT A.BSEQ,A.GSEQ,A.BNAME,B.GID,B.GNAME,B.CNT_METER,B.CNT_MODEM,B.RNAME\r\n"
			+ "FROM BUILDING AS A\r\n"
			+ "JOIN (SELECT T1.GSEQ,T1.RSEQ,T1.GID,T1.GNAME,T1.CNT_METER,T1.CNT_MODEM,T2.RNAME FROM GROUPSET AS T1\r\n"
			+ "JOIN REGION AS T2\r\n" + "ON T1.RSEQ=T2.RSEQ\r\n" + "WHERE T1.GSEQ=:gseq) AS B\r\n"
			+ "ON A.GSEQ=B.GSEQ) AA\r\n" + "JOIN \r\n"
			+ "(SELECT C.DID,C.DCU_IP,C.FEP_IP,C.FWV,C.S_SYS_STATE,D.BSEQ\r\n" + "FROM DCU_INFO AS C\r\n"
			+ "LEFT JOIN BUILDING_DCU_MAP AS D\r\n" + "ON C.DID=D.DID WHERE C.IS_DELETE='N') BB\r\n"
			+ "ON AA.BSEQ=BB.BSEQ", nativeQuery = true)
	public List<DcuInfoInterfaceVO> getDcuList(@Param("gseq") int gseq);

	@Transactional
	@Modifying
	@Query(value = "UPDATE DCU_INFO SET IS_DELETE='Y' WHERE DID = :did", nativeQuery = true)
	public void setDcuDelete(@Param("did") String did);

	@Query(value = "SELECT T1.DID,T2.BSEQ,T3.BNAME,T3.GSEQ,T4.GNAME,T4.RSEQ,T5.RNAME FROM (SELECT DID FROM DCU_INFO WHERE DID=:dcuId) AS T1\r\n"
			+ "JOIN BUILDING_DCU_MAP AS T2 ON T1.DID=T2.DID\r\n" + "JOIN BUILDING AS T3\r\n" + "ON T2.BSEQ=T3.BSEQ\r\n"
			+ "JOIN GROUPSET AS T4\r\n" + "ON T3.GSEQ=T4.GSEQ\r\n" + "JOIN REGION AS T5\r\n"
			+ "ON T4.RSEQ=T5.RSEQ", nativeQuery = true)
	public DeviceBuildingInterfaceVO getDcuMappInfo(@Param("dcuId") String dcuId);

	@Transactional
	@Modifying
	@Query(value = "UPDATE DCU_INFO SET DCU_IP = :dcuIp WHERE DID = :did", nativeQuery = true)
	public void setDcuIp(@Param("did") String did, @Param("dcuIp") String dcuIp);

	@Transactional
	@Modifying
	@Query(value = "UPDATE DCU_INFO SET DCU_PORT = :dcuPort WHERE DID = :did", nativeQuery = true)
	public void setDcuPort(@Param("did") String did, @Param("dcuPort") int dcuPort);

	@Transactional
	@Modifying
	@Query(value = "UPDATE DCU_INFO SET ROUTER_IP = :routerIp WHERE DID = :did", nativeQuery = true)
	public void setRouterIp(@Param("did") String did, @Param("routerIp") String routerIp);

	@Transactional
	@Modifying
	@Query(value = "UPDATE DCU_INFO SET LAT = :lat, LON = :lon WHERE DID = :did", nativeQuery = true)
	public void setLatLon(@Param("did") String did, @Param("lat") float lat, @Param("lon") float lon);

}
