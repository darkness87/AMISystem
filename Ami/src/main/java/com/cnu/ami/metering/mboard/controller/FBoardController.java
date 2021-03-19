package com.cnu.ami.metering.mboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.metering.mboard.service.FBoardService;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote fboard api
 *
 */

@RestController
@RequestMapping(value = "/api/metering/fboard")
public class FBoardController {

	@Autowired
	FBoardService fBoardService;

	@Autowired
	PropertyData propertyData;

}
