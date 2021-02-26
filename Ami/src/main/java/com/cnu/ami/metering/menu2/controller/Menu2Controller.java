package com.cnu.ami.metering.menu2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.metering.menu2.service.Menu2Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote Menu1 api
 *
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/metering/menu2")
public class Menu2Controller {

	@Autowired
	Menu2Service menu2Service;

	@Autowired
	PropertyData propertyData;

//	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Object getTestData() throws Exception {
		log.info("=== Menu1 Controller ===");

		log.info("key : {}", propertyData.getData("test.menu2.key"));

		Object data = menu2Service.testReadData();

		return data;
	}

}
