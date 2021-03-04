package com.cnu.ami.metering.menu1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.metering.menu1.models.Menu1MeterLpVO;
import com.cnu.ami.metering.menu1.models.ModelLpTestVO;
import com.cnu.ami.metering.menu1.service.Menu1Service;

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

	@RequestMapping(value = "/test/test", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Object getTestData() throws Exception {
		log.info("=== Menu1 Controller ===");

		log.info("key : {}", propertyData.getData("test.menu1.key"));

		Object data = menu1Service.testReadData();

		return data;
	}

}
