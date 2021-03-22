package com.cnu.ami.device.equipment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.MeterInfoDAO;
import com.cnu.ami.device.equipment.dao.ModemInfoDAO;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoInterfaceVO;
import com.cnu.ami.device.equipment.dao.entity.MeterInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.MeterInfoInterfaceVO;
import com.cnu.ami.device.equipment.models.DcuInfoListVO;
import com.cnu.ami.device.equipment.models.DcuInfoVO;
import com.cnu.ami.device.equipment.models.DcuRegVO;
import com.cnu.ami.device.equipment.models.MeterInfoListVO;
import com.cnu.ami.device.equipment.models.MeterInfoVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoListVO;
import com.cnu.ami.device.equipment.service.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	DcuInfoDAO dcuInfoDAO;

	@Autowired
	MeterInfoDAO meterInfoDAO;

	@Autowired
	ModemInfoDAO modemInfoDAO;

	@Override
	public List<DcuInfoListVO> getDcuListData(int gseq) throws Exception {

		List<DcuInfoListVO> list = new ArrayList<DcuInfoListVO>();
		DcuInfoListVO dcuInfoListVO = new DcuInfoListVO();

		if (gseq == 0) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "검색 확인 바랍니다.");
		} else {
			List<DcuInfoInterfaceVO> data = dcuInfoDAO.getDcuList(gseq);

			for (int i = 0; data.size() > i; i++) {
				dcuInfoListVO = new DcuInfoListVO();

				dcuInfoListVO.setBuildingSeq(data.get(i).getBSEQ());
				dcuInfoListVO.setEstateSeq(data.get(i).getGSEQ());
				dcuInfoListVO.setBuildingName(data.get(i).getBNAME());
				dcuInfoListVO.setEstateId(data.get(i).getGID());
				dcuInfoListVO.setEstateName(data.get(i).getGNAME());
				dcuInfoListVO.setMeterCount(data.get(i).getCNT_METER());
				dcuInfoListVO.setModemCount(data.get(i).getCNT_MODEM());
				dcuInfoListVO.setRegionName(data.get(i).getRNAME());
				dcuInfoListVO.setDcuId(data.get(i).getDID());
				dcuInfoListVO.setDcuIp(data.get(i).getDCU_IP());
				dcuInfoListVO.setFepIp(data.get(i).getFEP_IP());
				dcuInfoListVO.setFirmwareVersion(data.get(i).getFWV());
				dcuInfoListVO.setSystemState(data.get(i).getS_SYS_STATE());

				list.add(dcuInfoListVO);
			}

		}

		return list;
	}

	@Override
	public DcuInfoVO getDcuData(String did) throws Exception {

		DcuInfoVO dcuInfoVO = new DcuInfoVO();

		if (did == null || did.equals("")) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "검색 확인 바랍니다.");
		} else {
			DcuInfoEntity dcu = dcuInfoDAO.findByDID(did);

			if (dcu == null) {
				throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "데이터가 없습니다.");
			}

			dcuInfoVO.setDcuId(dcu.getDID());
			dcuInfoVO.setFepIp(dcu.getFEP_IP());
			dcuInfoVO.setFepPort(dcu.getFEP_PORT());
			dcuInfoVO.setDcuIp(dcu.getDCU_IP());
			dcuInfoVO.setDcuPort(dcu.getDCU_PORT());
			dcuInfoVO.setMacA(dcu.getMAC_A());
			dcuInfoVO.setMacB(dcu.getMAC_B());
			dcuInfoVO.setMacC(dcu.getMAC_C());
			dcuInfoVO.setITime(dcu.getITIME());
			dcuInfoVO.setFirmwareVersion(dcu.getFWV());
			dcuInfoVO.setWanCode(dcu.getWAN_CODE());
			dcuInfoVO.setCommCode(dcu.getCOMM_CODE());
			dcuInfoVO.setTMask(dcu.getT_MASK());
			dcuInfoVO.setSTypePeriod(dcu.getSM_P());
			dcuInfoVO.setSTypeLpPeriod(dcu.getSMLP_P());
			dcuInfoVO.setETypelpPeriod(dcu.getEMLP_P());
			dcuInfoVO.setGTypelpPeriod(dcu.getGMLP_P());
			dcuInfoVO.setEaTypeLpPeriod(dcu.getEAMLP_P());
			dcuInfoVO.setGTypeAvgVoltagePeriod(dcu.getGM_AVE_VA_P());
			dcuInfoVO.setGTypeInstVoltagePeriod(dcu.getGM_INST_VA_P());
			dcuInfoVO.setEaTypeAvgVoltagePeriod(dcu.getEAM_AVE_VA_P());
			dcuInfoVO.setEaTypeInstVoltagePeriod(dcu.getEAM_INST_VA_P());
			dcuInfoVO.setFirmwareBuild(dcu.getF_BUILD());
			dcuInfoVO.setPacketLength(dcu.getP_LENGTH());
			dcuInfoVO.setTimeOut(dcu.getTIME_OUT());
			dcuInfoVO.setTrapInterval(dcu.getTRAP_INTERVAL());
			dcuInfoVO.setEtypeTimeErrorLimit(dcu.getE_MTIME_ERROR_LIMIT());
			dcuInfoVO.setGtypeTimeErrorLimit(dcu.getG_MTIME_ERROR_LIMIT());
			dcuInfoVO.setEatypeTimeErrorLimit(dcu.getEA_MTIME_ERROR_LIMIT());
			dcuInfoVO.setETypeTimePeriod(dcu.getEM_TIME_P());
			dcuInfoVO.setGTypeTimePeriod(dcu.getGM_TIME_P());
			dcuInfoVO.setEaTypeTimePeriod(dcu.getEAM_TIME_P());
			dcuInfoVO.setCpuReset(dcu.getCPU_RESET());

		}

		return dcuInfoVO;
	}

	@Override
	public int setDcuData(DcuRegVO dcuRegVO) throws Exception {

		DcuInfoEntity dcuInfoEntity = new DcuInfoEntity();

		dcuInfoEntity.setDID(dcuRegVO.getDcuId());
		dcuInfoEntity.setDCU_IP(dcuRegVO.getDcuIp());
		dcuInfoEntity.setWDATE(new Date().getTime() / 1000);
		dcuInfoEntity.setUDATE(new Date().getTime() / 1000);

		try {
			dcuInfoDAO.save(dcuInfoEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public List<MeterInfoListVO> getMeterListData(int gseq) throws Exception {

		if (gseq == 0) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "검색 확인 바랍니다.");
		}

		List<MeterInfoInterfaceVO> meter = meterInfoDAO.getMeterList(gseq);

		if (meter == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "조회된 결과가 없습니다.");
		}

		List<MeterInfoListVO> list = new ArrayList<MeterInfoListVO>();
		MeterInfoListVO meterInfoListVO = new MeterInfoListVO();

		for (int i = 0; meter.size() > i; i++) {
			meterInfoListVO = new MeterInfoListVO();

			meterInfoListVO.setRegionSeq(meter.get(i).getRSeq());
			meterInfoListVO.setEstateSeq(meter.get(i).getGSeq());
			meterInfoListVO.setBuildingSeq(meter.get(i).getBSeq());
			meterInfoListVO.setRegionName(meter.get(i).getRName());
			meterInfoListVO.setEstateId(meter.get(i).getGId());
			meterInfoListVO.setEstateName(meter.get(i).getGName());
			meterInfoListVO.setBuildingName(meter.get(i).getBName());
			meterInfoListVO.setHouseName(meter.get(i).getHo());
			meterInfoListVO.setDcuId(meter.get(i).getDId());
			meterInfoListVO.setMeterId(meter.get(i).getMeter_Id());
			meterInfoListVO.setMac(meter.get(i).getMac());

			list.add(meterInfoListVO);
		}

		return list;
	}

	@Override
	public MeterInfoVO getMeterData(String meterid) throws Exception {

		if (meterid.length() != 11) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "계량기 번호는 11자리입니다.");
		}

		MeterInfoEntity meter = meterInfoDAO.findByMETERID(meterid);

		if (meter == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "Meter ID 정보를 확인해주세요.");
		}

		MeterInfoVO meterInfoVO = new MeterInfoVO();

		meterInfoVO.setMeterId(meter.getMETERID());
		meterInfoVO.setMac(meter.getMAC());
		meterInfoVO.setDcuId(meter.getDID());
		meterInfoVO.setDeviceName(meter.getDEVICE_NAME());
		meterInfoVO.setMeterReadingDay(meter.getMRD());
		meterInfoVO.setDcuTime(new Date(meter.getITIME() * 1000));
		meterInfoVO.setMeterTime(new Date(meter.getMTIME() * 1000));
		meterInfoVO.setLpPeriod(meter.getLP_PERIOD());
		meterInfoVO.setAcon(meter.getACON());
		meterInfoVO.setRcon(meter.getRCON());
		meterInfoVO.setPcon(meter.getPCON());
		meterInfoVO.setNetMetering(meter.getNET_METERING());
		meterInfoVO.setMComp(meter.getMCOMP());
		meterInfoVO.setMtype(meter.getMTYPE());
		meterInfoVO.setIsDelete(meter.getIS_DELETE());
		meterInfoVO.setWriteDate(new Date(meter.getWDATE() * 1000));
		meterInfoVO.setUpdateDate(new Date(meter.getUDATE() * 1000));

		return meterInfoVO;
	}

	@Override
	public List<MeterOtherInfoListVO> getOtherMeterListData(int gseq, int meterType) throws Exception {

		if (meterType != 1 || meterType != 2 || meterType != 3 || meterType != 4 || meterType != 5) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "계량기 타입이 존재하지 않습니다.");
		}

		List<MeterOtherInfoListVO> list = new ArrayList<MeterOtherInfoListVO>();

		MeterOtherInfoListVO meterOtherInfoListVO = new MeterOtherInfoListVO();

		for (int i = 0; 10 > i; i++) {
			meterOtherInfoListVO = new MeterOtherInfoListVO();

			meterOtherInfoListVO.setGatewayId("gateway");
			meterOtherInfoListVO.setMeterId("meter" + i);
			meterOtherInfoListVO.setMeterType(meterType);
			meterOtherInfoListVO.setRegDate(new Date());
			;

			list.add(meterOtherInfoListVO);
		}

		return list;
	}

}
