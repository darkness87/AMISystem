package com.cnu.ami.device.building.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.entity.BuildingHouseCountInterfaceVO;
import com.cnu.ami.device.building.dao.entity.HouseEntity;

@Repository
public interface HouseDAO extends JpaRepository<HouseEntity, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	@Query(value = "SELECT COUNT(*) AS COUNT FROM HOUSEHOLD WHERE GSEQ=:gseq", nativeQuery = true)
	public BuildingHouseCountInterfaceVO getHouseRegCount(@Param("gseq") int gSeq);

}
