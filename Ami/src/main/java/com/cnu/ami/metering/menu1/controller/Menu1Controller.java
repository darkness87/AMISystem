package com.cnu.ami.metering.menu1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.metering.menu1.service.Menu1Service;
import com.cnu.ami.testpackage.menu1.webflux.Test2VO;
import com.cnu.ami.testpackage.menu1.webflux.TestVO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote Menu1 api
 *
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/metering/menu1")
public class Menu1Controller {

	@Autowired
	Menu1Service menu1Service;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Object getTestData() throws Exception {
		log.info("=== Menu1 Controller ===");

		log.info("key : {}", propertyData.getData("test.menu1.key"));

		Object data = menu1Service.testReadData();

		return data;
	}
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Mono<TestVO> getTest2Data(){
		TestVO testVO = new TestVO();
		testVO.setId(1);
		testVO.setTitle("title hello");
		testVO.setMessage("hello. my name is webflux");
		
		return Mono.just(testVO); // 단일건은 Mono, 여러건을 리턴할때는 Flux를 사용 // 구독 개념을 적용하여 구현해봐야함
	}
	
	@RequestMapping(value = "/test3", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Flux<Object> getTest3Data(){
		TestVO testVO = new TestVO();
		testVO.setId(1);
		testVO.setTitle("title hello");
		testVO.setMessage("hello. my name is webflux");
		
		Test2VO test2VO = new Test2VO();
		test2VO.setId(2);
		test2VO.setTitle("title hello");
		test2VO.setMessage("hello. my name is webflux test2");
		test2VO.setCodeType(110);
		test2VO.setReply("OK");
		
		return Flux.just(testVO,test2VO); // 단일건은 Mono, 여러건을 리턴할때는 Flux를 사용
	}

}
