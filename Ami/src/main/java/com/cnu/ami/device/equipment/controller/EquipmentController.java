package com.cnu.ami.device.equipment.controller;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.ResultCountVO;
import com.cnu.ami.common.ResultVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.equipment.models.DcuInfoListVO;
import com.cnu.ami.device.equipment.models.DcuInfoVO;
import com.cnu.ami.device.equipment.models.DcuRealtimeStatusVO;
import com.cnu.ami.device.equipment.models.DcuRegVO;
import com.cnu.ami.device.equipment.models.MeterInfoListVO;
import com.cnu.ami.device.equipment.models.MeterInfoVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoListVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoVO;
import com.cnu.ami.device.equipment.models.SetDcuCheckIntervalVO;
import com.cnu.ami.device.equipment.models.SetDcuInfoVO;
import com.cnu.ami.device.equipment.models.SetDcuSecureFactorVO;
import com.cnu.ami.device.equipment.models.SetDcuTimeLimitVO;
import com.cnu.ami.device.equipment.service.EquipmentService;
import com.cnu.network.client.fep.CnuComm;
import com.dreamsecurity.amicipher.AMICipher;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 설비 장비관리
 * 
 * @author sookwon
 * @apiNote equipment api
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/device/equipment")
public class EquipmentController {

	@Autowired
	EquipmentService equipmentService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/dcu/count", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 총 DCU 수")
	public Mono<ResponseVO<ResultCountVO>> getDcuCount(HttpServletRequest request) throws Exception {

		ResultCountVO data = equipmentService.getDcuCount();

		return Mono.just(new ResponseVO<ResultCountVO>(request, data));
	}

	@RequestMapping(value = "/dcu/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 목록")
	public Mono<ResponseListVO<DcuInfoListVO>> getDcuListData(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int estateSeq) throws Exception {

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

	@RequestMapping(value = "/dcu/update/dcuip", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU IP 수정")
	public Mono<ResponseVO<ResultVO>> updateDcuIp(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp) throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = equipmentService.setDcuIp(dcuId, dcuIp);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/update/port", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU Port 수정")
	public Mono<ResponseVO<ResultVO>> updateDcuPort(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam int dcuPort) throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = equipmentService.setDcuPort(dcuId, dcuPort);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/update/routerip", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU IP 수정")
	public Mono<ResponseVO<ResultVO>> updateRouterIp(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String routerIp) throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = equipmentService.setRouterIp(dcuId, routerIp);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/update/location", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU IP 수정")
	public Mono<ResponseVO<ResultVO>> updateRouterIp(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam float latitude, @RequestParam float longitude) throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = equipmentService.setLatLon(dcuId, latitude, longitude);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/meter/count", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 총 METER 수")
	public Mono<ResponseVO<ResultCountVO>> getMeterCount(HttpServletRequest request) throws Exception {

		ResultCountVO data = equipmentService.getMeterCount();

		return Mono.just(new ResponseVO<ResultCountVO>(request, data));
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
	public Mono<ResponseVO<MeterInfoVO>> getMeterData(HttpServletRequest request, @RequestParam int estateSeq,
			@RequestParam String meterId) throws Exception {

		MeterInfoVO data = equipmentService.getMeterData(estateSeq, meterId);

		return Mono.just(new ResponseVO<MeterInfoVO>(request, data));
	}

	@RequestMapping(value = "/meter/delete", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : Meter 삭제")
	public Mono<ResponseVO<ResultVO>> deleteMeterData(HttpServletRequest request, @RequestParam String meterId)
			throws Exception {

		ResultVO resultVO = new ResultVO();
		int data = equipmentService.setMeterDelete(meterId);

		if (data == 0) { // 0: Success , 1: Fail
			resultVO.setResult(true);
		} else {
			resultVO.setResult(false);
		}

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/other/count", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 총 가스,수도,온수,난방 수")
	public Mono<ResponseVO<ResultCountVO>> getOtherCount(HttpServletRequest request) throws Exception {

		ResultCountVO data = equipmentService.getOtherMeterCount();

		return Mono.just(new ResponseVO<ResultCountVO>(request, data));
	}

	@RequestMapping(value = "/other/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 가스,수도,온수,난방 미터 목록")
	public Mono<ResponseListVO<MeterOtherInfoListVO>> getOtherListData(HttpServletRequest request,
			@RequestParam int estateSeq) throws Exception {

		List<MeterOtherInfoListVO> data = equipmentService.getOtherMeterListData(estateSeq);

		return Mono.just(new ResponseListVO<MeterOtherInfoListVO>(request, data));
	}

	@RequestMapping(value = "/other/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 가스,수도,온수,난방 미터 상세정보")
	public Mono<ResponseVO<MeterOtherInfoVO>> getOtherData(HttpServletRequest request, @RequestParam int estateSeq,
			@RequestParam String gatewayId, @RequestParam String meterId) throws Exception {

		MeterOtherInfoVO data = equipmentService.getOtherMeterData(estateSeq, gatewayId, meterId);

		return Mono.just(new ResponseVO<MeterOtherInfoVO>(request, data));
	}

	@RequestMapping(value = "/dcu/realtime/status", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 실시간 상태 정보")
	public Flux<ResponseVO<DcuRealtimeStatusVO>> getDcuRealTimeStatus(HttpServletRequest request,
			@RequestParam String dcuId, @RequestParam String dcuIp,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(
					new ResponseVO<DcuRealtimeStatusVO>(request, equipmentService.getDcuRealTimeStatus(dcuId, dcuIp)));
		} else {

			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<DcuRealtimeStatusVO>(request,
							equipmentService.getDcuRealTimeStatus(dcuId, dcuIp));
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}

	}

	@RequestMapping(value = "/dcu/setting/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 정보설정")
	public Mono<ResponseVO<ResultVO>> setDcuInfo(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp, @RequestBody SetDcuInfoVO setDcuInfoVO) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		boolean bool = comm.setDcuInfo(setDcuInfoVO.getFepIp(), setDcuInfoVO.getFepPort(), setDcuInfoVO.getTMask(),
				setDcuInfoVO.getSmP(), setDcuInfoVO.getSmlpP(), setDcuInfoVO.getEmlpP(), setDcuInfoVO.getGmlpP(),
				setDcuInfoVO.getEamlpP(), setDcuInfoVO.getGmAveVaP(), setDcuInfoVO.getGmInstVaP(),
				setDcuInfoVO.getEamAveVaP(), setDcuInfoVO.getEamInstVaP(), setDcuInfoVO.getPLength(),
				setDcuInfoVO.getTimeout(), setDcuInfoVO.getTrapItv(), setDcuInfoVO.getEmTimeP(),
				setDcuInfoVO.getGmTimeP(), setDcuInfoVO.getEamTimeP(), setDcuInfoVO.getCpuReset());

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/setting/time", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 시간설정")
	public Mono<ResponseVO<ResultVO>> setDcuTime(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean bool = comm.setDcuTime(dateFormat.format(date));

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/setting/timelimit", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 시간오차한계")
	public Mono<ResponseVO<ResultVO>> setDcuTimeLimit(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp, @RequestBody SetDcuTimeLimitVO setDcuTimeLimitVO) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		boolean bool = comm.setDcuTimeLimit(setDcuTimeLimitVO.getIMtype(), setDcuTimeLimitVO.getITimeLimit());

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/setting/interval", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 시간확인주기")
	public Mono<ResponseVO<ResultVO>> setDcuCheckInterval(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp, @RequestBody SetDcuCheckIntervalVO setDcuCheckIntervalVO) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		boolean bool = comm.setDcuCheckInterval(setDcuCheckIntervalVO.getIMtype(),
				setDcuCheckIntervalVO.getIInterval());

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/setting/factory", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 보안항목설정")
	public Mono<ResponseVO<ResultVO>> setDcuSecureFactor(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp, @RequestBody SetDcuSecureFactorVO setDcuSecureFactorVO) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		boolean bool = comm.setDcuSecureFactor(setDcuSecureFactorVO.getPnid(), setDcuSecureFactorVO.getOsPw(),
				setDcuSecureFactorVO.getAcodeRo(), setDcuSecureFactorVO.getAcodeRw(), setDcuSecureFactorVO.getSnmpRo(),
				setDcuSecureFactorVO.getSnmpRw());

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/setting/reboot", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 재부팅")
	public Mono<ResponseVO<ResultVO>> setDcuReboot(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		try {
			AMICipher jni = new AMICipher();
			log.info("AMICipher VERSION = {}", jni.amiGetVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean bool = comm.execDcuReboot();

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/dcu/setting/rescan", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : DCU 모뎀 재스캔")
	public Mono<ResponseVO<ResultVO>> setRescanModem(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp, @RequestParam String mac) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		// TODO DCU ID에 해당하는 모뎀 MAC 가져오기
		boolean bool = comm.rescanModem(mac);

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/meter/setting/time", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 계량기 시간설정")
	public Mono<ResponseVO<ResultVO>> setMeterTime(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp, @RequestParam String meterId) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		String[] meters = { meterId };

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		boolean bool = comm.setMeterTime(meters, dateFormat.format(date));

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/meter/setting/readingday", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 계량기 검침일 설정")
	public Mono<ResponseVO<ResultVO>> setMeterMrd(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp, @RequestParam String meterId, @RequestParam int day) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		String[] meters = { meterId };

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, day);

		date = new Date(cal.getTimeInMillis());

		boolean bool = comm.setMeterTime(meters, dateFormat.format(date));

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

	@RequestMapping(value = "/meter/setting/period", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "설비:장비관리 : 계량기 주기 설정")
	public Mono<ResponseVO<ResultVO>> setMeterLpPeriod(HttpServletRequest request, @RequestParam String dcuId,
			@RequestParam String dcuIp, @RequestParam String meterId, @RequestParam int period) throws Exception {

		log.info("{} , {} , {}", request, dcuId, dcuIp);

		ResultVO resultVO = new ResultVO();
		CnuComm comm = new CnuComm(dcuId, dcuIp); // DCU ID, DCU IP

		String[] meters = { meterId };

		boolean bool = comm.setMeterLpPeriod(meters, period);

		log.info("result : {}", bool);

		resultVO.setResult(bool);

		return Mono.just(new ResponseVO<ResultVO>(request, resultVO));
	}

}
