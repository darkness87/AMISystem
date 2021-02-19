package com.cnu.ami.metering.menu2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.metering.menu2.models.Menu2VO;

@Repository
public interface Menu2DAO extends JpaRepository<Menu2VO, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	public List<Menu2VO> findById(long id); // 찾고자 할때 find By 컬럼명(첫글자 대문자) 그 다음 컬럼 존재시 _ 로 이어져서 사용 // ** 함수명 중요 !!!

}
