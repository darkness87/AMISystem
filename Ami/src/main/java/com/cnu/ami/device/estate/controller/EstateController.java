package com.cnu.ami.device.estate.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.ResultVO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.estate.models.EstateVO;
import com.cnu.ami.device.estate.service.EstateService;

import reactor.core.publisher.Mono;

/**
 * 설비 단지관리
 * 
 * @author skchae@cnuglobal.com
 * @apiNote estate api
 *
 */

@RestController
@RequestMapping(value = "/api/device/estate")
public class EstateController {

	@Autowired
	EstateService estateService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:단지관리 : 단지리스트정보")
	public Mono<ResponseListVO<EstateVO>> getEstateListData(HttpServletRequest request) throws Exception {

		List<EstateVO> data = estateService.getEstateListData();

		return Mono.just(new ResponseListVO<EstateVO>(request, data));
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:단지관리 : 단지상세정보")
	public Mono<ResponseVO<EstateVO>> getEstateData(HttpServletRequest request, @RequestParam String gId)
			throws Exception {

		EstateVO data = estateService.getEstateData(gId);

		return Mono.just(new ResponseVO<EstateVO>(request, data));
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:단지관리 : 단지 등록")
	public Mono<ResponseVO<ResultVO>> setEstateData(HttpServletRequest request, @RequestBody EstateEntity estateEntity)
			throws Exception {

		int data = estateService.setEstateData(estateEntity);

		ResultVO resultVO = new ResultVO();
		if (data == 0) {
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

}
