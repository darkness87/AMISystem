package com.cnu.ami.metering.mboard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.mboard.models.lpTestInfoVO;

@Repository
public interface LpTestInfoDAO extends JpaRepository<lpTestInfoVO, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	public List<lpTestInfoVO> findByMeterid(String meterid);
	
}
