package com.cnu.ami.scheduler.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author sookwon OS 정보 가져오기
 */
@Slf4j
@Component
public class OsBatch {

	@SuppressWarnings("restriction")
	@Scheduled(fixedDelay = 1000 * 60 * 60) // TODO
	public void task() {
		log.info("===================================================================================");
		// JVM memory
		float gb = 1024 * 1024 * 1024;
		long heapSize = Runtime.getRuntime().totalMemory();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		long heapUseSize = heapSize - heapFreeSize;
		log.info("JVM heap 사용량(GB) => Used:{},Free:{},Total:{},Max:{}",
				String.format("%.3f", (double) heapUseSize / gb), String.format("%.3f", (double) heapFreeSize / gb),
				String.format("%.3f", (double) heapSize / gb), String.format("%.3f", (double) heapMaxSize / gb));

		// OS
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		log.info("OS CPU Usage : {}%", String.format("%.3f", osBean.getSystemCpuLoad() * 100));
		log.info("OS Memory Free Space : {}GB",
				String.format("%.3f", (double) osBean.getFreePhysicalMemorySize() / gb));
		log.info("OS Memory Used Space : {}GB", String.format("%.3f",
				(double) (osBean.getTotalPhysicalMemorySize() - osBean.getFreePhysicalMemorySize()) / gb));
		log.info("OS Memory Total Space : {}GB",
				String.format("%.3f", (double) osBean.getTotalPhysicalMemorySize() / gb));

		// 프로세스별
		/*
		 * PID : 프로세스 ID %CPU : cpu 사용 비율 %MEM : 메모리 사용 비율 VSZ : 가상메모리 사용량 (kb단위) RSS :
		 * 실제 메모리 사용량 (kb단위) STAT : 현재 프로세스 상태 TIME : 총 CPU 시간 COMMAND : 실행된 명령어
		 */
		log.info("===================================================================================");
		log.info("USER       PID %CPU %MEM VSZ RSS TTY STAT START   TIME COMMAND");
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "ps -aux | grep java | grep -v grep");
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				log.info(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "ps -aux | grep redis | grep -v grep");
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				log.info(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("===================================================================================\n");
	}
}
