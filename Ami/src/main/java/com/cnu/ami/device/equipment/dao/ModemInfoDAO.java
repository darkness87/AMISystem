package com.cnu.ami.device.equipment.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.equipment.dao.entity.ModemInfoEntity;

@Repository
public interface ModemInfoDAO extends JpaRepository<ModemInfoEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

	@Query(value = "SELECT COUNT(*) AS COUNT FROM MODEM_INFO A\r\n" + 
			"JOIN (SELECT DID FROM DCU_INFO WHERE IS_DELETE = 'N') B\r\n" + 
			"ON A.DID=B.DID", nativeQuery = true)
	public int getModemCount();
	
}
