package com.cnu.ami.metering.info.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.metering.info.dao.entity.DcuInterfaceVO;

@Repository
public interface DcuDAO extends JpaRepository<DcuInfoEntity, Long> {

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T1.DID \r\n"
			+ "FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n" + "RIGHT JOIN BUILDING_DCU_MAP AS B\r\n"
			+ "ON A.BSEQ=B.BSEQ) T1\r\n" + "LEFT JOIN\r\n"
			+ "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + "LEFT JOIN REGION AS B\r\n"
			+ "ON A.RSEQ=B.RSEQ) T2\r\n" + "ON T1.GSEQ=T2.GSEQ", nativeQuery = true)
	List<DcuInterfaceVO> getDcuData();

	@Query(value = "SELECT T2.RSEQ,T1.GSEQ,T1.BSEQ,T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T1.DID\r\n"
			+ "FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n" + "RIGHT JOIN BUILDING_DCU_MAP AS B\r\n"
			+ "ON A.BSEQ=B.BSEQ\r\n" + "WHERE A.GSEQ=:gseq) T1\r\n" + "LEFT JOIN\r\n"
			+ "(SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + "LEFT JOIN REGION AS B\r\n"
			+ "ON A.RSEQ=B.RSEQ\r\n" + "WHERE A.GSEQ=:gseq) T2\r\n" + "ON T1.GSEQ=T2.GSEQ", nativeQuery = true)
	List<DcuInterfaceVO> getDcuData(@Param("gseq") int gseq);

}
