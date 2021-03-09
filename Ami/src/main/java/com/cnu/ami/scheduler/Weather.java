package com.cnu.ami.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * 
 * @author sookwon 날씨 데이터 가져오기
 */

public class Weather {

	@Scheduled(fixedDelay = 1000 * 60 * 60) // TODO
	public void task() {

	}
}
