package com.cnu.ami.device.nms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.device.nms.models.NmsDcuListVO;
import com.cnu.ami.device.nms.service.NmsService;

import reactor.core.publisher.Mono;

/**
 * 설비 NMS
 * 
 * @author sookwon
 * @apiNote nms api
 */

@RestController
@RequestMapping(value = "/api/device/nms")
public class NmsController {

	@Autowired
	NmsService nmsService;
	
	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/dcu/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:NMS : DCU 리스트정보")
	public Mono<ResponseListVO<NmsDcuListVO>> getDCUList(HttpServletRequest request, @RequestParam int estateSeq) throws Exception {

		List<NmsDcuListVO> data = nmsService.getDcuList(estateSeq);

		return Mono.just(new ResponseListVO<NmsDcuListVO>(request, data));
	}

	@RequestMapping(value = "/meter/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:NMS : METER 리스트정보")
	public Mono<ResponseVO<Object>> getModemMeterList(HttpServletRequest request, @RequestParam String dcuId) throws Exception {

		Object data = new Object();

		return Mono.just(new ResponseVO<Object>(request, data));
	}

}
