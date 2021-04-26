package com.cnu.ami.device.building.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.entity.BuildingHouseCountInterfaceVO;
import com.cnu.ami.device.building.dao.entity.HouseEntity;
import com.cnu.ami.device.building.dao.entity.HouseInterfaceVO;

@Repository
public interface HouseDAO extends JpaRepository<HouseEntity, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	@Query(value = "SELECT COUNT(*) AS COUNT FROM HOUSEHOLD WHERE GSEQ=:gseq", nativeQuery = true)
	public BuildingHouseCountInterfaceVO getHouseRegCount(@Param("gseq") int gSeq);

	@Query(value = "SELECT HO FROM HOUSEHOLD WHERE HSEQ = (SELECT HSEQ FROM HOUSE_METER_MAP WHERE METER_ID = :meterid)", nativeQuery = true)
	public String getHouseHoInfo(@Param("meterid") String meterid);

	@Query(value = "SELECT METER_ID,HO FROM (SELECT HSEQ,HO FROM HOUSEHOLD WHERE BSEQ = :bseq) AS T1\r\n"
			+ "JOIN HOUSE_METER_MAP AS T2 ON T1.HSEQ=T2.HSEQ", nativeQuery = true)
	public List<HouseInterfaceVO> getHouseHoList(@Param("bseq") int bseq);

}
