package com.cnu.ami.support.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.support.dao.entity.AskCodeEntity;

@Repository
public interface AskCodeDAO extends JpaRepository<AskCodeEntity, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

}
