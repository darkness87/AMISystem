package com.cnu.ami.metering.menu1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.metering.menu1.models.Menu1MeterLpVO;
import com.cnu.ami.metering.menu1.models.ModelLpTestVO;
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
	public Mono<TestVO> getTest2Data() {
		TestVO testVO = new TestVO();
		testVO.setId(1);
		testVO.setTitle("title mono");
		testVO.setMessage("hello. webflux Mono");

		return Mono.just(testVO); // 단일건은 Mono, 여러건을 리턴할때는 Flux를 사용 // 구독 개념을 적용하여 구현해봐야함
	}

	@RequestMapping(value = "/test3", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Flux<Object> getTest3Data() {
		TestVO testVO = new TestVO();
		testVO.setId(1);
		testVO.setTitle("title flux");
		testVO.setMessage("hello. webflux Flux");

		Test2VO test2VO = new Test2VO();
		test2VO.setId(2);
		test2VO.setTitle("title flux 2");
		test2VO.setMessage("hello. webflux Flux 2");
		test2VO.setCodeType(110);
		test2VO.setReply("OK");

		return Flux.just(testVO, test2VO); // 단일건은 Mono, 여러건을 리턴할때는 Flux를 사용
	}

	// create
	@RequestMapping(value = "/test4", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public int setTestData(String meterid, float lp) throws Exception {
		log.info("=== Menu1 Set ===");
		return menu1Service.testInsertData(meterid, lp);
	}

	// join
	@RequestMapping(value = "/test5", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Mono<List<Menu1MeterLpVO>> setJoinData() throws Exception {
		log.info("=== Menu1 Join ===");

		List<Menu1MeterLpVO> data = new ArrayList<Menu1MeterLpVO>();

		data = menu1Service.testjoinData();

		return Mono.just(data);

	}

	// Test Join
	@RequestMapping(value = "/test6", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Mono<List<ModelLpTestVO>> getJoinData() throws Exception {
		log.info("=== Menu1 test6 Join ===");

		List<ModelLpTestVO> data = new ArrayList<ModelLpTestVO>();

		data = menu1Service.testjoinLpData();

		return Mono.just(data);

	}

}
