package com.cnu.ami.device.mapping.controller;

import java.util.ArrayList;
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
	PropertyData propertyData;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 리스트정보")
	public Mono<ResponseListVO<Object>> getTestListData(HttpServletRequest request) throws Exception {

		List<Object> data = new ArrayList<Object>();

		return Mono.just(new ResponseListVO<Object>(request, data));
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 상세정보")
	public Mono<ResponseVO<Object>> getTestData(HttpServletRequest request, @RequestParam String id) throws Exception {

		Object data = new Object();

		return Mono.just(new ResponseVO<Object>(request, data));
	}

}
