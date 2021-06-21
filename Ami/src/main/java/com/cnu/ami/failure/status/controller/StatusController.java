package com.cnu.ami.failure.status.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.ResultVO;
import com.cnu.ami.failure.status.models.DcuFailureStatusVO;
import com.cnu.ami.failure.status.service.StatusService;
import com.cnu.network.client.fep.CnuComm;
import com.dreamsecurity.amicipher.AMICipher;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 네트워크 상태코드
 * 
 * @author sookwon
 * @apiNote status api
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/failure/status")
public class StatusController {

	@Autowired
	StatusService statusService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애:네트워크 상태관리 : 리스트정보")
	public Mono<ResponseListVO<DcuFailureStatusVO>> getDcuStatus(HttpServletRequest request,
			@RequestParam int estateSeq) throws Exception {

		List<DcuFailureStatusVO> data = statusService.getDcuStatus(estateSeq);

		return Mono.just(new ResponseListVO<DcuFailureStatusVO>(request, data));
	}

	@RequestMapping(value = "/device/ping", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애:네트워크 상태관리 : 장비 설비 핑 체크")
	public Mono<ResponseVO<String>> getDevicePing(HttpServletRequest request, @RequestParam String ip)
			throws Exception {

		String pingResult = "";
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "ping -c 3 " + ip);
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			int count = 0;

			while ((line = reader.readLine()) != null) {
				log.info(line);
				count++;
				pingResult = pingResult + " \r\n " + line;
			}

			log.info("count : {} / pingResult : {}", count, pingResult);

			if (pingResult == null || pingResult.equals("")) {
				return Mono.just(new ResponseVO<String>(request, "Ping "+ip+" Result null"));
			}

			pingResult = pingResult + " \r\n ";

		} catch (IOException e) {
			e.printStackTrace();
			return Mono.just(new ResponseVO<String>(request, "Ping Exception"));
		}

		return Mono.just(new ResponseVO<String>(request, pingResult));
	}

	@RequestMapping(value = "/dcu/reboot", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애:네트워크 상태관리 : DCU 리부트")
	public Mono<ResponseVO<ResultVO>> setDcuReboot(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		try {
			AMICipher jni = new AMICipher();
			log.info("AMICipher VERSION = {}", jni.amiGetVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean bool = comm.execDcuReboot();
		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

// TODO
//	@RequestMapping(value = "/test/device/ping", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Description(value = "장애:네트워크 상태관리 : 장비 설비 핑 체크 플럭스 테스트")
//	public Flux<ResponseVO<String>> getDeviceFluxPing(HttpServletRequest request, @RequestParam String ip)
//			throws Exception {
//
//		ProcessBuilder processBuilder = new ProcessBuilder();
//		processBuilder.command("bash", "-c", "ping -c 5 " + ip);
//
//		Process process = processBuilder.start();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//		String line;
//
//		while ((line = reader.readLine()) != null) {
//			log.info(line);
//		}
//
//		return Flux.just(new ResponseVO<String>(request, line));
//	}

}
