package com.cnu.ami.failure.status.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.failure.status.dao.entity.DcuFailureStatusInterfaceVO;

@Repository
public interface DcuFailureStatusDAO extends JpaRepository<DcuInfoEntity, String> {

	@Query(value = "SELECT T1.BNAME,T3.DID,T3.DCU_IP,T3.DCU_PORT,T3.ROUTER_IP,T3.S_SYS_STATE,T3.DSTATUS\r\n"
			+ "FROM (SELECT BNAME,BSEQ FROM BUILDING WHERE GSEQ = :gseq) AS T1\r\n" + "JOIN BUILDING_DCU_MAP AS T2\r\n"
			+ "ON T1.BSEQ=T2.BSEQ\r\n" + "JOIN (SELECT * FROM DCU_INFO WHERE DSTATUS != \"RET_SUCCESS\") AS T3\r\n"
			+ "ON T2.DID=T3.DID", nativeQuery = true)
	public List<DcuFailureStatusInterfaceVO> getDcuFailureStatus(@Param("gseq") int gseq);

}
