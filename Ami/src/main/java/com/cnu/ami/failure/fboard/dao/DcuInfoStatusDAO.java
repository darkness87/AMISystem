package com.cnu.ami.failure.fboard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnu.ami.failure.fboard.dao.entity.DcuInfoStatusEntity;

@Repository
public interface DcuInfoStatusDAO extends JpaRepository<DcuInfoStatusEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

	@Query(value = "SELECT * FROM DCU_INFO_STATUS WHERE DSTATUS !='RET_SUCCESS' AND YMD = :ymd", nativeQuery = true)
	List<DcuInfoStatusEntity> getDcuFailure(@Param("ymd") String ymd);

}
