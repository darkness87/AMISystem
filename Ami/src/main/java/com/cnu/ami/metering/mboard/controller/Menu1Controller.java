package com.cnu.ami.metering.mboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.metering.mboard.service.Menu1Service;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote Menu1 api
 *
 */

@RestController
@RequestMapping(value = "/api/metering/menu1")
public class Menu1Controller {

	@Autowired
	Menu1Service menu1Service;

	@Autowired
	PropertyData propertyData;

}
