package com.cnu.ami.support.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.support.dao.entity.AskEntity;

@Repository
public interface AskDAO extends JpaRepository<AskEntity, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

	public List<AskEntity> findById(long id); // 찾고자 할때 find By 컬럼명(첫글자 대문자) 그 다음 컬럼 존재시 _ 로 이어져서 사용 // ** 함수명 중요 !!!

}
