package com.cnu.ami.metering.mboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.metering.mboard.service.MBoardService;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote mboard api
 *
 */

@RestController
@RequestMapping(value = "/api/metering/mboard")
public class MBoardController {

	@Autowired
	MBoardService mBoardService;

	@Autowired
	PropertyData propertyData;

}
