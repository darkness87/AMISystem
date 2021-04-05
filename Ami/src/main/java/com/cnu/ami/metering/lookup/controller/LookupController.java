package com.cnu.ami.metering.lookup.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.metering.lookup.service.LookupService;

import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote lookup api (검침조회)
 *
 */

@RestController
@RequestMapping(value = "/api/metering/lookup")
public class LookupController {

	@Autowired
	LookupService lookupService;

	@RequestMapping(value = "/test/lp", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침 : ")
	public Mono<ResponseListVO<Object>> getMeteringData(HttpServletRequest request) throws Exception {

		List<Object> data = lookupService.getLpRepo();
		
		List<Object> data2 = lookupService.getLpTemp();

		return Mono.just(new ResponseListVO<Object>(request, data));
	}

}
