package com.cnu.ami.device.equipment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoInterfaceVO;

@Repository
public interface DcuInfoDAO extends JpaRepository<DcuInfoEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

	DcuInfoEntity findByDID(String did);

	@Query(value = "SELECT IFNULL(AA.BSEQ,0) AS BSEQ,IFNULL(AA.GSEQ,0) AS GSEQ,AA.BNAME,AA.GID,AA.GNAME,IFNULL(AA.CNT_METER,0) AS CNT_METER,IFNULL(AA.CNT_MODEM,0) AS CNT_MODEM,AA.RNAME,BB.DID,BB.DCU_IP,BB.FEP_IP,BB.FWV,BB.S_SYS_STATE\r\n"
			+ "FROM (SELECT A.BSEQ,A.GSEQ,A.BNAME,B.GID,B.GNAME,B.CNT_METER,B.CNT_MODEM,B.RNAME\r\n"
			+ "FROM BUILDING AS A\r\n"
			+ "JOIN (SELECT T1.GSEQ,T1.RSEQ,T1.GID,T1.GNAME,T1.CNT_METER,T1.CNT_MODEM,T2.RNAME FROM GROUPSET AS T1\r\n"
			+ "JOIN REGION AS T2\r\n" + "ON T1.RSEQ=T2.RSEQ\r\n" + ") AS B\r\n"
			+ "ON A.GSEQ=B.GSEQ) AA\r\n" + "RIGHT JOIN \r\n"
			+ "(SELECT C.DID,C.DCU_IP,C.FEP_IP,C.FWV,C.S_SYS_STATE,D.BSEQ\r\n" + "FROM DCU_INFO AS C\r\n"
			+ "LEFT JOIN BUILDING_DCU_MAP AS D\r\n" + "ON C.DID=D.DID WHERE C.IS_DELETE='N') BB\r\n"
			+ "ON AA.BSEQ=BB.BSEQ", nativeQuery = true)
	public List<DcuInfoInterfaceVO> getDcuList();
	
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
	
	@Query(value = "UPDATE DCU_INFO SET IS_DELETE='Y' WHERE DID = :did", nativeQuery = true)
	public void setDcuDelete(@Param("did") String did);

}
