package com.cnu.ami.device.server.service.impl;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.MeterInfoDAO;
import com.cnu.ami.device.equipment.dao.ModemInfoDAO;
import com.cnu.ami.device.server.dao.ServerDAO;
import com.cnu.ami.device.server.dao.entity.ServerEntity;
import com.cnu.ami.device.server.models.ServerListVO;
import com.cnu.ami.device.server.models.ServerProcessVO;
import com.cnu.ami.device.server.models.ServerRegistrationVO;
import com.cnu.ami.device.server.models.ServerSystemVO;
import com.cnu.ami.device.server.service.ServerService;
import com.sun.management.OperatingSystemMXBean;

@Service
public class ServerServiceImpl implements ServerService {

	@Autowired
	private ServerDAO serverDAO;
	
	@Autowired
	private DcuInfoDAO dcuInfoDAO;

	@Autowired
	private MeterInfoDAO meterInfoDAO;

	@Autowired
	private ModemInfoDAO modemInfoDAO;

	@SuppressWarnings("restriction")
	@Override
	public ServerSystemVO getServerSystemInfo() throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ServerSystemVO serverSystemVO = new ServerSystemVO();

		// JVM memory
		float gb = 1024 * 1024 * 1024;
		long heapSize = Runtime.getRuntime().totalMemory();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		long heapUseSize = heapSize - heapFreeSize;

		// OS
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

		serverSystemVO.setDate(dateFormat.format(date));
		serverSystemVO.setOsCpu(String.format("%.3f", osBean.getSystemCpuLoad() * 100));
		serverSystemVO.setOsMemory(
				String.format("%.3f", ((osBean.getTotalPhysicalMemorySize() - osBean.getFreePhysicalMemorySize()) / gb)
						/ (osBean.getTotalPhysicalMemorySize() / gb) * 100));
		serverSystemVO.setJvmUsed(String.format("%.3f", (double) heapUseSize / gb));
		serverSystemVO.setJvmFree(String.format("%.3f", (double) heapFreeSize / gb));
		serverSystemVO.setJvmTotal(String.format("%.3f", (double) heapSize / gb));
		serverSystemVO.setJvmMax(String.format("%.3f", (double) heapMaxSize / gb));

		return serverSystemVO;

	}

	@Override
	public ServerRegistrationVO getServerRegistration() throws Exception {
		
		ServerRegistrationVO serverRegistrationVO = new ServerRegistrationVO();
		
		serverRegistrationVO.setDate(new Date());
		serverRegistrationVO.setServerCount(serverDAO.count());
		serverRegistrationVO.setDcuCount(dcuInfoDAO.getDcuCount());
		serverRegistrationVO.setModemCount(modemInfoDAO.count());
		serverRegistrationVO.setMeterCount(meterInfoDAO.getMeterCount());
		
		return serverRegistrationVO;
	}
	
	@Override
	public List<ServerListVO> getServerList() throws Exception {
		List<ServerEntity> data = serverDAO.findAll();

		List<ServerListVO> list = new ArrayList<ServerListVO>();
		ServerListVO serverListVO = new ServerListVO();

		for (ServerEntity server : data) {
			serverListVO = new ServerListVO();

			serverListVO.setServerSeq(server.getSSEQ());
			serverListVO.setServerName(server.getSNAME());
			serverListVO.setModel(server.getMODEL());
			serverListVO.setIp(server.getIP());
			serverListVO.setPurpose(server.getPURPOSE());
			serverListVO.setStatus(server.getSTATUS());
			serverListVO.setWriteDate(new Date(server.getWDATE() * 1000));
			serverListVO.setRegionSeq(server.getRSEQ());

			list.add(serverListVO);
		}

		return list;
	}

	@Override
	public List<ServerProcessVO> getServerProcess() throws Exception {
		// TODO 서버 프로세스 정보 추가하기

		List<ServerProcessVO> list = new ArrayList<ServerProcessVO>();
		ServerProcessVO serverProcessVO = new ServerProcessVO();

		serverProcessVO.setProcess("");
		serverProcessVO.setProcessName("");

		list.add(serverProcessVO);

		return list;
	}

}
