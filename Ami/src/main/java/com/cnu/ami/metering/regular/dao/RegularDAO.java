package com.cnu.ami.metering.regular.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.regular.dao.entity.RegularMonthEntity;
import com.cnu.ami.metering.regular.dao.entity.RegularMonthInterfaceVO;

@Repository
public interface RegularDAO extends JpaRepository<RegularMonthEntity, Long> {

	@Query(value = "SELECT T2.RNAME,T2.GID,T2.GNAME,T1.BNAME,T4.HO,T1.DID,T3.METER_ID,T3.MRD,T3.MAC,\r\n" + 
			"IFNULL(T5.MTIME,0) AS TO_MTIME, IFNULL(T5.APT1,0) AS TO_APT1, IFNULL(T5.R_APT1,0) AS TO_R_APT1, IFNULL(T6.MTIME,0) AS FROM_MTIME, IFNULL(T6.APT1,0) AS FROM_APT1, IFNULL(T6.R_APT1,0) AS FROM_R_APT1, IFNULL(T6.APT1-T5.APT1,0) AS F_USE, IFNULL(T6.R_APT1-T5.R_APT1,0) AS R_USE\r\n" + 
			"FROM(SELECT A.BSEQ,A.GSEQ,A.BNAME,B.DID FROM BUILDING AS A\r\n" + 
			"RIGHT JOIN BUILDING_DCU_MAP AS B ON A.BSEQ=B.BSEQ WHERE A.GSEQ=:gseq) T1\r\n" + 
			"LEFT JOIN (SELECT A.GSEQ,A.RSEQ,A.GID,A.GNAME,B.RNAME FROM GROUPSET AS A\r\n" + 
			"LEFT JOIN REGION AS B ON A.RSEQ=B.RSEQ WHERE A.GSEQ=:gseq) T2 ON T1.GSEQ=T2.GSEQ\r\n" + 
			"LEFT JOIN METER_INFO AS T3 ON T1.DID=T3.DID\r\n" + 
			"LEFT JOIN (SELECT A.GSEQ,A.BSEQ,A.HSEQ,A.HO,B.METER_ID FROM HOUSEHOLD AS A\r\n" + 
			"LEFT JOIN HOUSE_METER_MAP AS B ON A.HSEQ=B.HSEQ WHERE A.GSEQ=:gseq) AS T4 ON T3.METER_ID=T4.METER_ID\r\n" + 
			"LEFT JOIN (SELECT METER_ID,MTIME,APT1,R_APT1 FROM GAUGE_PERIOD WHERE FROM_UNIXTIME(MTIME) = :toDate) AS T5 ON T3.METER_ID=T5.METER_ID\r\n" + 
			"LEFT JOIN (SELECT METER_ID,MTIME,APT1,R_APT1 FROM GAUGE_PERIOD WHERE FROM_UNIXTIME(MTIME) = :fromDate) AS T6 ON T3.METER_ID=T6.METER_ID\r\n" + 
			"ORDER BY T1.BNAME ASC, T1.DID ASC, T4.HO ASC" + "", nativeQuery = true)
	List<RegularMonthInterfaceVO> getRegularData(@Param("gseq") int gseq,@Param("toDate") String toDate,@Param("fromDate") String fromDate);

}
