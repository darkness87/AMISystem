package com.cnu.ami.failure.fboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.failure.fboard.service.FBoardService;


/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote FBoard api
 *
 */

@RestController
@RequestMapping(value = "/api/failure/fboard")
public class FBoardController {

	@Autowired
	FBoardService fBoardService;

	@Autowired
	PropertyData propertyData;

}
