package com.cnu.ami.device.server.service.impl;

import org.springframework.stereotype.Service;

import com.cnu.ami.device.server.models.ServerSystemVO;
import com.cnu.ami.device.server.service.ServerService;

@Service
public class ServerServiceImpl implements ServerService{

	@Override
	public ServerSystemVO getServerSystemInfo() throws Exception {
		// TODO 서버 정보 넘기기
		return null;
	}

}
