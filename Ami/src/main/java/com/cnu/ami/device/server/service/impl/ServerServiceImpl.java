package com.cnu.ami.device.server.service.impl;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cnu.ami.device.server.models.ServerSystemVO;
import com.cnu.ami.device.server.service.ServerService;
import com.sun.management.OperatingSystemMXBean;

@Service
public class ServerServiceImpl implements ServerService {

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

}
