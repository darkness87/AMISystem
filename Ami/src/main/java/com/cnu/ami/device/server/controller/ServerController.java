package com.cnu.ami.device.server.controller;

import java.time.Duration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.dashboard.service.DashBoardService;
import com.cnu.ami.device.server.models.ServerListVO;
import com.cnu.ami.device.server.models.ServerProcessVO;
import com.cnu.ami.device.server.models.ServerRegistrationVO;
import com.cnu.ami.device.server.models.ServerSystemVO;
import com.cnu.ami.device.server.service.ServerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 설비 서버현황
 * 
 * @author sookwon
 * @apiNote server api
 */

@RestController
@RequestMapping(value = "/api/device/server")
public class ServerController {

	@Autowired
	ServerService serverService;

	@Autowired
	DashBoardService dashBoardService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/system", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:서버현황 : 시스템 자원정보")
	public Flux<ResponseVO<ServerSystemVO>> getSystemData(HttpServletRequest request, @RequestParam(required = false, defaultValue = "0") int duration)
			throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<ServerSystemVO>(request, serverService.getServerSystemInfo()));
		} else {

			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<ServerSystemVO>(request, serverService.getServerSystemInfo());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}

	}

	@RequestMapping(value = "/registration/device", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:서버현황 : 등록설비정보")
	public Mono<ResponseVO<ServerRegistrationVO>> getElectricRegistrationDevice(HttpServletRequest request)
			throws Exception {
		ServerRegistrationVO data = serverService.getServerRegistration();

		return Mono.just(new ResponseVO<ServerRegistrationVO>(request, data));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:서버현황 : 서버정보")
	public Mono<ResponseListVO<ServerListVO>> getServerList(HttpServletRequest request) throws Exception {

		List<ServerListVO> data = serverService.getServerList();

		return Mono.just(new ResponseListVO<ServerListVO>(request, data));
	}

	@RequestMapping(value = "/list/detail", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:서버현황 : 서버현황정보 상세")
	public Mono<ResponseListVO<ServerProcessVO>> getServerListDetail(HttpServletRequest request) throws Exception {

		List<ServerProcessVO> data = serverService.getServerProcess();
		
		return Mono.just(new ResponseListVO<ServerProcessVO>(request, data));
	}

}
