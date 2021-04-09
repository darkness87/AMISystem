package com.cnu.ami.device.equipment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.cnu.ami.device.equipment.models.DcuInfoListVO;
import com.cnu.ami.device.equipment.models.DcuInfoVO;
import com.cnu.ami.device.equipment.models.DcuRegVO;
import com.cnu.ami.device.equipment.models.MeterInfoListVO;
import com.cnu.ami.device.equipment.models.MeterInfoVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoListVO;
import com.cnu.ami.device.equipment.service.EquipmentService;
import com.cnu.network.client.fep.CnuComm;

import reactor.core.publisher.Mono;

/**
 * 설비 장비관리
 * 
 * @author sookwon
 * @apiNote equipment api
 */

@RestController
@RequestMapping(value = "/api/device/equipment")
public class EquipmentController {

	@Autowired
	EquipmentService equipmentService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/dcu/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 목록")
	public Mono<ResponseListVO<DcuInfoListVO>> getDcuListData(HttpServletRequest request, @RequestParam int estateSeq)
			throws Exception {

		List<DcuInfoListVO> data = equipmentService.getDcuListData(estateSeq);

		return Mono.just(new ResponseListVO<DcuInfoListVO>(request, data));
	}

	@RequestMapping(value = "/dcu/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 상세정보")
	public Mono<ResponseVO<DcuInfoVO>> getDCUData(HttpServletRequest request, @RequestParam String dcuId)
			throws Exception {

		DcuInfoVO data = equipmentService.getDcuData(dcuId);

		return Mono.just(new ResponseVO<DcuInfoVO>(request, data));
	}

	@RequestMapping(value = "/dcu/registration", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 등록 - 기본정보")
	public Mono<ResponseVO<ResultVO>> setDCUData(HttpServletRequest request, @RequestBody DcuRegVO dcuRegVO)
			throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = equipmentService.setDcuData(dcuRegVO);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 삭제")
	public Mono<ResponseVO<ResultVO>> deleteDCUData(HttpServletRequest request, @RequestParam String dcuId)
			throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = equipmentService.setDcuDelete(dcuId);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/meter/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : METER 목록")
	public Mono<ResponseListVO<MeterInfoListVO>> getMeterListData(HttpServletRequest request,
			@RequestParam int estateSeq) throws Exception {

		List<MeterInfoListVO> data = equipmentService.getMeterListData(estateSeq);

		return Mono.just(new ResponseListVO<MeterInfoListVO>(request, data));
	}

	@RequestMapping(value = "/meter/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : METER 상세정보")
	public Mono<ResponseVO<MeterInfoVO>> getMeterData(HttpServletRequest request, @RequestParam String meterId)
			throws Exception {

		MeterInfoVO data = equipmentService.getMeterData(meterId);

		return Mono.just(new ResponseVO<MeterInfoVO>(request, data));
	}

	@RequestMapping(value = "/other/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 가스,수도,온수,난방 미터 목록")
	public Mono<ResponseListVO<MeterOtherInfoListVO>> getOtherListData(HttpServletRequest request,
			@RequestParam int estateSeq, @RequestParam int meterType) throws Exception {

		List<MeterOtherInfoListVO> data = equipmentService.getOtherMeterListData(estateSeq, meterType);

		return Mono.just(new ResponseListVO<MeterOtherInfoListVO>(request, data));
	}

	@RequestMapping(value = "/other/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 가스,수도,온수,난방 미터 상세정보")
	public Mono<ResponseVO<Object>> getOtherData(HttpServletRequest request, @RequestParam String meterId,
			@RequestParam int meterType) throws Exception {

		MeterOtherInfoListVO data = equipmentService.getOtherMeterData(meterId, meterType);

		return Mono.just(new ResponseVO<Object>(request, data));
	}

	@RequestMapping(value = "/test/dcu/setting", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 설정")
	public Mono<ResponseVO<ResultVO>> setDcuCommSet(HttpServletRequest request) throws Exception {

		ResultVO resultVO = new ResultVO();

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// TODO
		// Service 단에서 구현 하도록 변경
		// 서버의 시간을 클라이언트에게 제공하고 사용자는 시간확인 후 설정 요청 이상시 수정필요
		CnuComm comm = new CnuComm("CNU_TEST_1", "192.168.123.208"); // DCU ID, DCU IP
		boolean bool = comm.setDcuTime(dateFormat.format(date));

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

}
