package com.cnu.ami.device.building.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.device.building.dao.entity.BuildingEntity;

@Repository
public interface BuildingDAO extends JpaRepository<BuildingEntity, Long> { // 키 값이 숫자 일경우 Long, 문자열 String

}
