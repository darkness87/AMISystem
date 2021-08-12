package com.cnu.ami.device.nms.controller;

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

import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.ResultVO;
import com.cnu.ami.device.nms.models.MasterModemListVO;
import com.cnu.ami.device.nms.models.NmsDcuCheckListVO;
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

	@RequestMapping(value = "/dcu/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:NMS : DCU 리스트정보")
	public Mono<ResponseListVO<NmsDcuListVO>> getDCUList(HttpServletRequest request, @RequestParam int estateSeq)
			throws Exception {

		List<NmsDcuListVO> data = nmsService.getDcuList(estateSeq);

		return Mono.just(new ResponseListVO<NmsDcuListVO>(request, data));
	}

	@RequestMapping(value = "/dcu/reboot/list", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:NMS : DCU 리부트 리스트")
	public Mono<ResponseVO<ResultVO>> setDCURebootList(HttpServletRequest request,
			@RequestBody List<NmsDcuCheckListVO> nmsDcuCheckListVO) throws Exception {

		boolean bool = nmsService.setDCURebootList(nmsDcuCheckListVO);

		ResultVO resultVO = new ResultVO();
		resultVO.setResult(bool);
		// TODO 성공 DCU 수, 실패 DCU 수로 세분화 필요

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/modem/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:NMS : MODEM 리스트정보")
	public Mono<ResponseListVO<MasterModemListVO>> getModemMeterList(HttpServletRequest request, @RequestParam String dcuId)
			throws Exception {

		List<MasterModemListVO> data = nmsService.getModemMeterList(dcuId);

		return Mono.just(new ResponseListVO<MasterModemListVO>(request, data));
	}
	
	@RequestMapping(value = "/dcu/firmware/list", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:NMS : DCU 펌웨어 리스트")
	public Mono<ResponseVO<ResultVO>> setDCUFirmwareList(HttpServletRequest request,
			@RequestBody List<NmsDcuCheckListVO> nmsDcuCheckListVO) throws Exception {

		boolean bool = nmsService.setDCUFirmwareList(nmsDcuCheckListVO);

		ResultVO resultVO = new ResultVO();
		resultVO.setResult(bool);
		// TODO 성공 DCU 수, 실패 DCU 수로 세분화 필요

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}
	
	@RequestMapping(value = "/modem/firmware/list", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:NMS : MODEM 펌웨어 리스트")
	public Mono<ResponseVO<ResultVO>> setMODEMFirmwareList(HttpServletRequest request,
			@RequestBody List<NmsDcuCheckListVO> nmsDcuCheckListVO) throws Exception {

		boolean bool = nmsService.setMODEMFirmwareList(nmsDcuCheckListVO);

		ResultVO resultVO = new ResultVO();
		resultVO.setResult(bool);
		// TODO 성공 DCU 수, 실패 DCU 수로 세분화 필요

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

}
