package com.cnu.ami.metering.menu1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.menu1.models.Menu1MeterLpVO;

@Repository
public interface Menu1JoinDAO extends JpaRepository<Menu1MeterLpVO, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	public List<Menu1MeterLpVO> findAll();
	
}
