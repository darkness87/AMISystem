package com.cnu.ami.scheduler.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnu.ami.scheduler.dao.entity.WeatherEntity;

@Repository
public interface WeatherDAO extends JpaRepository<WeatherEntity, Long> {

	WeatherEntity findFirstByRSEQAndFCSTDATEOrderByFCSTTIMEDesc(long rseq, String fcstDate);

}
