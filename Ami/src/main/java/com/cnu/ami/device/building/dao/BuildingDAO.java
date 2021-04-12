package com.cnu.ami.device.building.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.entity.BuildingEntity;
import com.cnu.ami.device.building.dao.entity.BuildingHouseCountInterfaceVO;
import com.cnu.ami.device.building.dao.entity.BuildingInterfaceVO;

@Repository
public interface BuildingDAO extends JpaRepository<BuildingEntity, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	@Query(value = "SELECT AA.BSEQ AS BSEQ,AA.GSEQ AS GSEQ,AA.BNAME AS BNAME,AA.GID AS GID,AA.GNAME AS GNAME,AA.RSEQ AS RSEQ,CC.RNAME\r\n" + 
			"FROM (SELECT A.BSEQ,A.GSEQ,A.BNAME,B.GID,B.GNAME,B.RSEQ FROM BUILDING AS A\r\n" + 
			"JOIN GROUPSET AS B ON A.GSEQ=B.GSEQ) AA JOIN \r\n" + 
			"(SELECT RSEQ,RNAME FROM REGION) AS CC\r\n" + 
			"ON CC.RSEQ=AA.RSEQ", nativeQuery = true)
	public List<BuildingInterfaceVO> getBuildingList();

	@Query(value = "SELECT AA.BSEQ AS BSEQ,AA.GSEQ AS GSEQ,AA.BNAME AS BNAME,AA.GID AS GID,AA.GNAME AS GNAME,AA.RSEQ AS RSEQ,CC.RNAME\r\n" + 
			"FROM (SELECT A.BSEQ,A.GSEQ,A.BNAME,B.GID,B.GNAME,B.RSEQ FROM BUILDING AS A\r\n" + 
			"JOIN GROUPSET AS B ON A.GSEQ=B.GSEQ WHERE B.GSEQ=:gseq) AA JOIN \r\n" + 
			"(SELECT RSEQ,RNAME FROM REGION) AS CC\r\n" + 
			"ON CC.RSEQ=AA.RSEQ", nativeQuery = true)
	public List<BuildingInterfaceVO> getBuildingList(@Param("gseq") int gseq);

	@Query(value = "SELECT AA.BSEQ AS BSEQ,AA.GSEQ AS GSEQ,AA.BNAME AS BNAME,AA.GID AS GID,AA.GNAME AS GNAME,AA.RSEQ AS RSEQ,CC.RNAME\r\n" + 
			"FROM (SELECT A.BSEQ,A.GSEQ,A.BNAME,B.GID,B.GNAME,B.RSEQ FROM BUILDING AS A\r\n" + 
			"JOIN GROUPSET AS B ON A.GSEQ=B.GSEQ) AA JOIN \r\n" + 
			"(SELECT RSEQ,RNAME FROM REGION WHERE RSEQ=:rseq) AS CC\r\n" + 
			"ON CC.RSEQ=AA.RSEQ", nativeQuery = true)
	public List<BuildingInterfaceVO> getBuildingRegionList(@Param("rseq") int rseq);

	public BuildingEntity findByBSEQ(int bSeq);

	@Query(value = "SELECT COUNT(*) AS COUNT FROM BUILDING WHERE GSEQ=:gseq", nativeQuery = true)
	public BuildingHouseCountInterfaceVO getBuildingRegCount(@Param("gseq") int gSeq);

	public BuildingEntity findFirstByGSEQAndBNAME(int gSeq, String bName);

	public BuildingEntity findFirstByBSEQAndGSEQAndBNAME(int bSeq, int gSeq, String bName);

	public void deleteByBSEQ(int bSeq);
}
