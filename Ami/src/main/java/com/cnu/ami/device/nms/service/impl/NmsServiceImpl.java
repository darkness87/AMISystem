package com.cnu.ami.device.nms.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.DcuNmsInterfaceVO;
import com.cnu.ami.device.nms.dao.ModemDAO;
import com.cnu.ami.device.nms.dao.entity.ModemTreeInterfaceVO;
import com.cnu.ami.device.nms.models.MasterModemListVO;
import com.cnu.ami.device.nms.models.NmsDcuListVO;
import com.cnu.ami.device.nms.models.NmsDcuRebootListVO;
import com.cnu.ami.device.nms.models.StepMeterListVO;
import com.cnu.ami.device.nms.models.StepModemListVO;
import com.cnu.ami.device.nms.service.NmsService;
import com.cnu.ami.metering.info.dao.RealTimeDAO;
import com.cnu.ami.metering.info.dao.entity.RealTimeDcuInterfaceVO;
import com.cnu.network.client.fep.CnuComm;
import com.dreamsecurity.amicipher.AMICipher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NmsServiceImpl implements NmsService {

	@Autowired
	DcuInfoDAO dcuInfoDAO;

	@Autowired
	ModemDAO modemDAO;

	@Autowired
	RealTimeDAO realTimeDAO;

	@Override
	public List<NmsDcuListVO> getDcuList(int gseq) throws Exception {
		List<DcuNmsInterfaceVO> data = dcuInfoDAO.getDcuNmsList(gseq);

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "조회된 결과가 없습니다.");
		}

		List<NmsDcuListVO> list = new ArrayList<NmsDcuListVO>();
		NmsDcuListVO nmsDcuListVO = new NmsDcuListVO();

		for (DcuNmsInterfaceVO dcu : data) {
			nmsDcuListVO = new NmsDcuListVO();

			nmsDcuListVO.setBuildingName(dcu.getBNAME());
			nmsDcuListVO.setEstateId(dcu.getGID());
			nmsDcuListVO.setEstateName(dcu.getGNAME());
			nmsDcuListVO.setMeterCount(dcu.getCNT_METER());
			nmsDcuListVO.setModemCount(dcu.getCNT_MODEM());
			nmsDcuListVO.setRegionName(dcu.getRNAME());
			nmsDcuListVO.setDcuId(dcu.getDID());
			nmsDcuListVO.setDcuIp(dcu.getDCU_IP());
			nmsDcuListVO.setDcuPort(dcu.getDCU_PORT());
			nmsDcuListVO.setFirmwareVersion(dcu.getFWV());
			
			String[] nmsVer = dcu.getS_SYS_DESCR().split("Ver:");
			
			nmsDcuListVO.setNmsVersion(nmsVer[1].replace(")", ""));
			nmsDcuListVO.setSysState(dcu.getS_SYS_STATE());
			nmsDcuListVO.setDcuStatus(dcu.getDSTATUS());

			list.add(nmsDcuListVO);
		}

		return list;
	}

	@Override
	public boolean setDCURebootList(List<NmsDcuRebootListVO> nmsDcuRebootListVO) throws Exception {
		// TODO 성공 DCU 수, 실패 DCU 수로 세분화 하여야 할듯

		boolean bool = false;

		for (NmsDcuRebootListVO list : nmsDcuRebootListVO) {

			CnuComm comm = new CnuComm(list.getDcuId(), list.getDcuId()); // DCU ID, DCU IP

			try {
				AMICipher jni = new AMICipher();
				log.info("AMICipher VERSION = {}", jni.amiGetVersion());
			} catch (Exception e) {
				e.printStackTrace();
			}

			bool = comm.execDcuReboot();

		}

		return bool;
	}

	@Override
	public List<MasterModemListVO> getModemMeterList(String dcuId) throws Exception {

		// TODO NMS 트리구조 관련
		List<ModemTreeInterfaceVO> data = modemDAO.getModemMeterTree(dcuId);

		List<RealTimeDcuInterfaceVO> meterInfo = realTimeDAO.getRealTimeDCUData(dcuId);

		DcuInfoEntity dcuInfo = dcuInfoDAO.findByDID(dcuId);

		List<StepMeterListVO> meter = new ArrayList<StepMeterListVO>();
		StepMeterListVO stepMeterListVO = new StepMeterListVO();

		List<StepModemListVO> step = new ArrayList<StepModemListVO>();
		StepModemListVO stepModemListVO = new StepModemListVO();

		List<MasterModemListVO> master = new ArrayList<MasterModemListVO>();
		MasterModemListVO masterModemListVO = new MasterModemListVO();

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long toTime = cal.getTimeInMillis();
		cal.add(Calendar.HOUR_OF_DAY, -12);
		long fromTime = cal.getTimeInMillis();

		for (ModemTreeInterfaceVO modem : data) {
			stepModemListVO = new StepModemListVO();
			
			stepModemListVO.setModemMac(modem.getMAC_STEP1());
			stepModemListVO.setModemStatus(modem.getREGI_STAT()); // 1:default(초기상태값), 2:Active(정상 동작중), 3:Suspend(통신금지), 4:RegAction(등록 중), 5:Fault(통신실패)
			stepModemListVO.setHardwareVersion(modem.getHWV_H());
			stepModemListVO.setProgramVersion(modem.getAPMV_H());
			String[] meterList = modem.getMETER_STEP1().split(";");

			stepModemListVO.setStepCount(meterList.length);

			meter = new ArrayList<StepMeterListVO>(); // 미터 리스트 초기화 필수
			
			for (RealTimeDcuInterfaceVO datalist : meterInfo) {
				
				for (int i = 0; i < meterList.length; i++) {

					if (datalist.getMETER_ID().equals(meterList[i])) {

						stepMeterListVO = new StepMeterListVO();

						stepMeterListVO.setMeterId(meterList[i]);
						stepMeterListVO.setHouseName(datalist.getHO());
						stepMeterListVO.setMeterTime(new Date(datalist.getMTIME() * 1000));
						stepMeterListVO.setFap(datalist.getFAP());

						// 시간 비교
						if (datalist.getMTIME() * 1000 >= fromTime && datalist.getMTIME() * 1000 <= toTime) {
							stepMeterListVO.setStatus(0);
						} else {
							stepMeterListVO.setStatus(1);
						}

						meter.add(stepMeterListVO);
						continue;
					}

				}

			}

			stepModemListVO.setStepMeter(meter);
			step.add(stepModemListVO);
		}

		masterModemListVO.setMasterModemMac(dcuInfo.getMAC_A());
		masterModemListVO.setModemCount(data.size());
		masterModemListVO.setMeterCount(meterInfo.size());
		masterModemListVO.setStepModem(step);

		master.add(masterModemListVO);

		return master;
	}

}
