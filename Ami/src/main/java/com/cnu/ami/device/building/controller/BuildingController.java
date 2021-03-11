package com.cnu.ami.device.building.controller;

import java.util.ArrayList;
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
import com.cnu.ami.device.building.models.BuildingVO;
import com.cnu.ami.device.building.service.BuildingService;

import reactor.core.publisher.Mono;

/**
 * 설비 동 관리
 * 
 * @author skchae@cnuglobal.com
 * @apiNote building api
 *
 */

@RestController
@RequestMapping(value = "/api/device/building")
public class BuildingController {

	@Autowired
	BuildingService buildingService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:동관리 : 동 리스트정보")
	public Mono<ResponseListVO<BuildingVO>> getBuildingListData(HttpServletRequest request) throws Exception {

		List<BuildingVO> data = new ArrayList<BuildingVO>();

		return Mono.just(new ResponseListVO<BuildingVO>(request, data));
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:동관리 : 동 상세정보")
	public Mono<ResponseVO<BuildingVO>> getBuildingData(HttpServletRequest request, @RequestParam String id)
			throws Exception {

		BuildingVO data = new BuildingVO();

		return Mono.just(new ResponseVO<BuildingVO>(request, data));
	}

}
