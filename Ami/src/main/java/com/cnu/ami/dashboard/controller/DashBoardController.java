package com.cnu.ami.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.dashboard.service.DashBoardService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote Main dashboard api
 *
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/dashboard")
public class DashBoardController {

	@Autowired
	DashBoardService dashBoardService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Object getTestData1() throws Exception {
		log.info("=== Comm Controller ===");

		Object data = dashBoardService.testReadData();

		return data;
	}

}
