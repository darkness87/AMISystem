package com.cnu.ami.device.estate.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.estate.dao.entity.EstateEntity;

@Repository
public interface EstateDAO extends JpaRepository<EstateEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

	public EstateEntity findBygId(String gId); // 찾고자 할때 find By 컬럼명(첫글자 대문자) 그 다음 컬럼 존재시 _ 로 이어져서 사용 // ** 함수명 중요 !!!

	public EstateEntity findBygSeq(int gSeq);
	
	public void deleteBygSeq(int gSeq);
	
}
