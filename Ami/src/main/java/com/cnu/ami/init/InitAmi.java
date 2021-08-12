package com.cnu.ami.init;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@EnableScheduling
//@Component
public class InitAmi {

//	@Scheduled(cron = "0/60 * * * * ?") // 매 60초마다
	public void run() {
		log.info("================ AMI service start!");
		// init();
		log.info("================ AMI service end!");
	}

//	private void init() {
//	}

}
