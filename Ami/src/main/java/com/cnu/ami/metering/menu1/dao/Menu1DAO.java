package com.cnu.ami.metering.menu1.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.menu1.models.Menu1VO;

@Repository
public interface Menu1DAO extends JpaRepository<Menu1VO, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	public List<Menu1VO> findById(long id); // 찾고자 할때 find By 컬럼명(첫글자 대문자) 그 다음 컬럼 존재시 _ 로 이어져서 사용 // ** 함수명 중요 !!!
	
	public List<Menu1VO> findFirst2ByMeterid(String meterid); // Limit 쿼리 사용시 findFirst , Top 쿼리 사용시 findTop

}
