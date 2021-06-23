package com.cnu.ami.failure.code.controller;

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
import com.cnu.ami.failure.code.models.CodeValueVO;
import com.cnu.ami.failure.code.service.CodeService;

import reactor.core.publisher.Mono;

/**
 * 장애 상태관리
 * 
 * @author sookwon
 * @apiNote code api
 */

@RestController
@RequestMapping(value = "/api/failure/code")
public class CodeController {

	@Autowired
	CodeService codeService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애:상태관리 : 리스트정보")
	public Mono<ResponseListVO<CodeValueVO>> getListData(HttpServletRequest request, @RequestParam int estateSeq,
			@RequestParam String dcuId, @RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam int statusCode) throws Exception {

		// gseq : 0 , dcuId = "" 일때 전체 조회
		// statusCode 정의
		// 1:과전류,2:정기검침,3:계절변경,4:모뎀 커버,5:터미널 커버,6:Latch ON,7:Latch OFF,8:Latch ERROR,9:Sag / Swell,10:오결선,11:온도,12:DST,
		// 13:자계 감지,14:부하제한 차단,15:현재 Tariff,16:현재 Tariff,17:SR,18:정전,19:시각 변경,20:수동검침,21:DR,22:배터리 없음,23:전압 결상,24:프로그램 변경,
		// 25:과전압,26:저전압,27:비설정 LP 기록주기,28:비정기검침
		
		List<CodeValueVO> data = codeService.getDataList(estateSeq, dcuId, fromDate, toDate, statusCode);

		return Mono.just(new ResponseListVO<CodeValueVO>(request, data));
	}

	@RequestMapping(value = "/status/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애:상태관리 : 리스트정보")
	public Mono<ResponseListVO<CodeValueVO>> getStatusListData(HttpServletRequest request, @RequestParam int estateSeq,
			@RequestParam long statusCode) throws Exception {

		List<CodeValueVO> data = codeService.getStatusDataList(estateSeq, statusCode);

		return Mono.just(new ResponseListVO<CodeValueVO>(request, data));
	}

}
