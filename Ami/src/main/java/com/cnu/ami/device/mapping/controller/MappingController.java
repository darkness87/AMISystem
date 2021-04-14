package com.cnu.ami.device.mapping.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.device.mapping.dao.document.MappingTemp;
import com.cnu.ami.device.mapping.service.MappingService;

import reactor.core.publisher.Mono;

/**
 * 설비 매핑관리
 * 
 * @author sookwon
 * @apiNote mapping api
 */

@RestController
@RequestMapping(value = "/api/device/mapping")
public class MappingController {

	@Autowired
	MappingService mappingService;
	
	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/estate", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 단지매핑정보")
	public Mono<ResponseListVO<Object>> getEstateMapp(HttpServletRequest request, @RequestParam int estateSeq)
			throws Exception {

		List<Object> data = new ArrayList<Object>();
		// sql에서 매핑된 정보를 가지고 와서 전달

		return Mono.just(new ResponseListVO<Object>(request, data));
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 단지매핑저장")
	public Mono<ResponseVO<Object>> setEstateMapp(HttpServletRequest request, @RequestBody MappingTemp mappingTemp)
			throws Exception {

		int data = mappingService.setEstateMapp(mappingTemp);
		// 1. mongoDB에 이력 저장
		// 2. sql에 선택,삭제,저장 등을 사용하여 매핑정보 저장

		return Mono.just(new ResponseVO<Object>(request, data));
	}

	@RequestMapping(value = "/histroy/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 이력 리스트정보")
	public Mono<ResponseListVO<Object>> getEstateMappHistory(HttpServletRequest request, @RequestParam int estateSeq)
			throws Exception {

		List<Object> data = new ArrayList<Object>();
		// 1. 해당 단지의 이력정보를 출력

		return Mono.just(new ResponseListVO<Object>(request, data));
	}

	@RequestMapping(value = "/histroy/detail", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 이력 상세정보")
	public Mono<ResponseListVO<Object>> getEstateMappHistoryDetail(HttpServletRequest request,
			@RequestParam int estateSeq, @RequestParam String date) throws Exception {

		List<Object> data = new ArrayList<Object>();

		return Mono.just(new ResponseListVO<Object>(request, data));
	}

}
