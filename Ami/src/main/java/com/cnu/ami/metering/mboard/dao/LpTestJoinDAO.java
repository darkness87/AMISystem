package com.cnu.ami.metering.mboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.mboard.models.lpTestDataVO;

@Repository
public interface LpTestJoinDAO extends JpaRepository<lpTestDataVO, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	//public List<lpTestInfoVO> findAll();
	
}
