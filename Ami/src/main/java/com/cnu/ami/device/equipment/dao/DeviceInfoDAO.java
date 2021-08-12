package com.cnu.ami.device.equipment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.DeviceEstateInterfaceVO;
import com.cnu.ami.device.equipment.dao.entity.DeviceInfoEntity;

@Repository
public interface DeviceInfoDAO extends JpaRepository<DeviceInfoEntity, String> {

	public List<DeviceInfoEntity> findByGSEQ(int gseq);
	
	@Query(value = "SELECT T1.GSEQ,T1.GNAME,T1.GID,T2.RSEQ,T2.RNAME FROM (SELECT GSEQ,GNAME,GID,RSEQ FROM GROUPSET WHERE GSEQ = :gseq) AS T1\r\n" + 
			"JOIN REGION AS T2 ON T1.RSEQ=T2.RSEQ\r\n" , nativeQuery = true)
	public DeviceEstateInterfaceVO getDeviceEstateInfo(@Param("gseq") int gseq);
	
	public DeviceInfoEntity findByGSEQAndGATEWAYIDAndMETERID(int gseq, String gatewayId, String meterId);

}
