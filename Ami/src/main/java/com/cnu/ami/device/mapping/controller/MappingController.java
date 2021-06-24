package com.cnu.ami.device.mapping.controller;

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
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.ResultVO;
import com.cnu.ami.device.mapping.dao.document.MappingTemp;
import com.cnu.ami.device.mapping.models.EstateMappingVO;
import com.cnu.ami.device.mapping.models.MappingVO;
import com.cnu.ami.device.mapping.service.MappingService;

import reactor.core.publisher.Mono;

/**
 * 설비 매핑관리
 * 
 * @author sookwon
 * @apiNote mapping api
 */

@RestController
@RequestMapping(value = "/api/device/mapping")
public class MappingController {

	@Autowired
	MappingService mappingService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/estate", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 단지매핑정보")
	public Mono<ResponseVO<MappingVO>> getEstateMapp(HttpServletRequest request, @RequestParam int estateSeq)
			throws Exception {

		// 1. mysql에서 매핑된 정보를 가지고 와서 전달 - join 관련

		MappingVO data = mappingService.getEstateMapp(estateSeq);

		return Mono.just(new ResponseVO<MappingVO>(request, data));
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 단지매핑저장")
	public Mono<ResponseVO<ResultVO>> setEstateMapp(HttpServletRequest request, @RequestBody MappingTemp mappingTemp)
			throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = mappingService.setEstateMapp(mappingTemp);
		// 1. mongoDB에 이력 저장 - 이력 저장시 기존과 비교하여 바뀐 수 set 필요
		// 2. sql에 선택,삭제,저장 등을 사용하여 매핑정보 저장 TODO 추후 예정
		
		// TODO  매핑 SQL 저장 및 업데이트
		// 1. 전달 받은 리스트 정보에서 동 정보를 그룹화하여 insert 또는 Update
		// 2. 
		

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/history/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 이력 리스트정보")
	public Mono<ResponseVO<EstateMappingVO>> getEstateMappHistory(HttpServletRequest request,
			@RequestParam int estateSeq) throws Exception {

		EstateMappingVO data = mappingService.getEstateMappHistory(estateSeq);
		// 1. 해당 단지의 이력정보를 출력 - MongoDB

		return Mono.just(new ResponseVO<EstateMappingVO>(request, data));
	}

	@RequestMapping(value = "/history/detail", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:매핑관리 : 이력 상세정보")
	public Mono<ResponseVO<MappingVO>> getEstateMappHistoryDetail(HttpServletRequest request,
			@RequestParam String mappingId) throws Exception {

		MappingVO data = mappingService.getEstateHistoryMapp(mappingId);
		// 1. 이력 리스트의 상세 - MongoDB
		// 2. 매핑정보 가져오기 - mysql
		// 3. java에서 매핑하여 던지기

		return Mono.just(new ResponseVO<MappingVO>(request, data));
	}

}
