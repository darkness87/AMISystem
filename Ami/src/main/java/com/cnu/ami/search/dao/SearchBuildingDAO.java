package com.cnu.ami.search.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.entity.BuildingEntity;
import com.cnu.ami.search.dao.entity.BuildingDcuInterfaceVO;

@Repository
public interface SearchBuildingDAO extends JpaRepository<BuildingEntity, Long> {

	public List<BuildingEntity> findByGSEQ(int gseq);

	@Query(value = "SELECT T1.BSEQ,T1.BNAME,T2.DID FROM (SELECT BSEQ,BNAME FROM BUILDING) AS T1\r\n"
			+ "JOIN BUILDING_DCU_MAP AS T2\r\n" + "ON T1.BSEQ=T2.BSEQ"
			+ "JOIN (SELECT DID FROM DCU_INFO WHERE IS_DELETE='N') T3\r\n" + "ON T2.DID=T3.DID", nativeQuery = true)
	public List<BuildingDcuInterfaceVO> getBuildingDcuList();

	@Query(value = "SELECT T1.BSEQ,T1.BNAME,T2.DID FROM (SELECT BSEQ,BNAME FROM BUILDING WHERE GSEQ = :gseq) AS T1\r\n"
			+ "JOIN BUILDING_DCU_MAP AS T2\r\n" + "ON T1.BSEQ=T2.BSEQ\r\n"
			+ "JOIN (SELECT DID FROM DCU_INFO WHERE IS_DELETE='N') T3\r\n" + "ON T2.DID=T3.DID", nativeQuery = true)
	public List<BuildingDcuInterfaceVO> getBuildingDcuList(@Param("gseq") int gseq);
}
