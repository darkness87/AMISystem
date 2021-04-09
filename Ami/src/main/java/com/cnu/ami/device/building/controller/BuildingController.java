package com.cnu.ami.device.building.controller;

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
import com.cnu.ami.device.building.models.BuildingVO;
import com.cnu.ami.device.building.models.DcuStatusVO;
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
	public Mono<ResponseListVO<BuildingVO>> getBuildingListData(HttpServletRequest request, @RequestParam int regionSeq, @RequestParam int estateSeq)
			throws Exception {

		List<BuildingVO> data = buildingService.getBuildingListData(regionSeq,estateSeq);

		return Mono.just(new ResponseListVO<BuildingVO>(request, data));
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:동관리 : 동 상세정보")
	public Mono<ResponseVO<BuildingVO>> getBuildingData(HttpServletRequest request, @RequestParam int estateSeq,
			@RequestParam int buildingSeq, @RequestParam String dcuId) throws Exception {

		BuildingVO buildingVO = new BuildingVO();
		buildingVO.setEstateSeq(estateSeq);
		buildingVO.setBuildingSeq(buildingSeq);
		buildingVO.setDcuId(dcuId);

		BuildingVO data = buildingService.getBulidingData(buildingVO);

		return Mono.just(new ResponseVO<BuildingVO>(request, data));
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:동관리 : 동 등록")
	public Mono<ResponseVO<ResultVO>> setBuildingData(HttpServletRequest request, @RequestBody BuildingVO buildingVO)
			throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = buildingService.setBulidingData(buildingVO);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}
	
	@RequestMapping(value = "/namecheck", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:동관리 : 동명 체크")
	public Mono<ResponseVO<ResultVO>> getBuildingCheck(HttpServletRequest request, @RequestParam int estateSeq,
			@RequestParam String buildingName) throws Exception {

		int data = buildingService.getBuildNameCheck(estateSeq,buildingName);

		ResultVO resultVO = new ResultVO();
		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}
		
		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}
	
	@RequestMapping(value = "/dcucheck", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:동관리 : DCU ID 체크")
	public Mono<ResponseVO<DcuStatusVO>> getDcuCheck(HttpServletRequest request, @RequestParam String dcuId) throws Exception {

		DcuStatusVO data = buildingService.getDcuIdCheck(dcuId);

		return Mono.just(new ResponseVO<DcuStatusVO>(request, data));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:동관리 : 동 수정")
	public Mono<ResponseVO<ResultVO>> updateBuildingDcuData(HttpServletRequest request, @RequestBody BuildingVO buildingVO)
			throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = buildingService.setBulidingDcuData(buildingVO);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:동관리 : 동 삭제")
	public Mono<ResponseVO<ResultVO>> deleteBuildingDcuMapp(HttpServletRequest request, @RequestParam String dcuId, @RequestParam int buildingSeq) throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = buildingService.setBuildingDelete(dcuId,buildingSeq);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

}
