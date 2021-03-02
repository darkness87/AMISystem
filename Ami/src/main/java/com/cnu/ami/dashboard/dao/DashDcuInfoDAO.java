package com.cnu.ami.dashboard.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.dashboard.dao.entity.DcuInfoEntity;

@Repository
public interface DashDcuInfoDAO extends JpaRepository<DcuInfoEntity, String> { // 키 값이 숫자 일경우 Long, 문자열 String

}
