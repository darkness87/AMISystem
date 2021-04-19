package com.cnu.ami.device.server.service;

import java.util.List;

import com.cnu.ami.device.server.models.ServerListVO;
import com.cnu.ami.device.server.models.ServerProcessVO;
import com.cnu.ami.device.server.models.ServerSystemVO;

public interface ServerService {

	public ServerSystemVO getServerSystemInfo() throws Exception;

	public List<ServerListVO> getServerList() throws Exception;

	public List<ServerProcessVO> getServerProcess() throws Exception;

}
