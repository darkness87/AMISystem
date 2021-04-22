package com.cnu.ami.device.equipment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.ResultCountVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.DeviceInfoDAO;
import com.cnu.ami.device.equipment.dao.MeterInfoDAO;
import com.cnu.ami.device.equipment.dao.ModemInfoDAO;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.DcuInfoInterfaceVO;
import com.cnu.ami.device.equipment.dao.entity.DeviceBuildingInterfaceVO;
import com.cnu.ami.device.equipment.dao.entity.DeviceEstateInterfaceVO;
import com.cnu.ami.device.equipment.dao.entity.DeviceInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.EstateMeterInfoInterfaceVO;
import com.cnu.ami.device.equipment.dao.entity.MeterInfoEntity;
import com.cnu.ami.device.equipment.dao.entity.MeterInfoInterfaceVO;
import com.cnu.ami.device.equipment.models.DcuInfoListVO;
import com.cnu.ami.device.equipment.models.DcuInfoVO;
import com.cnu.ami.device.equipment.models.DcuRegVO;
import com.cnu.ami.device.equipment.models.MeterInfoListVO;
import com.cnu.ami.device.equipment.models.MeterInfoVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoListVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoVO;
import com.cnu.ami.device.equipment.service.EquipmentService;
import com.cnu.ami.metering.info.dao.RealTimeDAO;
import com.cnu.ami.metering.info.dao.entity.RealTimeEntity;

@Service
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	DcuInfoDAO dcuInfoDAO;

	@Autowired
	MeterInfoDAO meterInfoDAO;

	@Autowired
	ModemInfoDAO modemInfoDAO;

	@Autowired
	DeviceInfoDAO deviceInfoDAO;

	@Autowired
	RealTimeDAO realTimeDAO;

	@Override
	public ResultCountVO getDcuCount() throws Exception {
		ResultCountVO resultCountVO = new ResultCountVO();
		resultCountVO.setCount(dcuInfoDAO.count());
		return resultCountVO;
	}

	@Override
	public List<DcuInfoListVO> getDcuListData(int gseq) throws Exception {

		List<DcuInfoListVO> list = new ArrayList<DcuInfoListVO>();
		DcuInfoListVO dcuInfoListVO = new DcuInfoListVO();

		List<DcuInfoInterfaceVO> data = null;

		if (gseq == 0) {
//			data = dcuInfoDAO.getDcuList();
			data = dcuInfoDAO.getDcuNoMappList(); // 미 매핑 정보
		} else {
			data = dcuInfoDAO.getDcuList(gseq);
		}

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "조회된 결과가 없습니다.");
		}

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

		return list;
	}

	@Override
	public DcuInfoVO getDcuData(String did) throws Exception {

		DcuInfoVO dcuInfoVO = new DcuInfoVO();

		if (did == null || did.equals("")) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "검색 확인 바랍니다.");
		} else {
			DcuInfoEntity dcu = dcuInfoDAO.findByDID(did);

			int count = meterInfoDAO.getDcuMeterCount(did);

			DeviceBuildingInterfaceVO mapp = dcuInfoDAO.getDcuMappInfo(did);

			if (dcu == null) {
				throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "데이터가 없습니다.");
			}

			if (mapp == null) {
				dcuInfoVO.setRegionName(null);
				dcuInfoVO.setEstateName(null);
				dcuInfoVO.setBuildingName(null);
			} else {
				dcuInfoVO.setRegionName(mapp.getRNAME());
				dcuInfoVO.setEstateName(mapp.getGNAME());
				dcuInfoVO.setBuildingName(mapp.getBNAME());
			}

			dcuInfoVO.setDcuId(dcu.getDID());
			dcuInfoVO.setFepIp(dcu.getFEP_IP());
			dcuInfoVO.setFepPort(dcu.getFEP_PORT());
			dcuInfoVO.setDcuIp(dcu.getDCU_IP());
			dcuInfoVO.setDcuPort(dcu.getDCU_PORT());
			dcuInfoVO.setMacA(dcu.getMAC_A());
			dcuInfoVO.setMacB(dcu.getMAC_B());
			dcuInfoVO.setMacC(dcu.getMAC_C());
			dcuInfoVO.setDcuCurrentTime(new Date(dcu.getITIME() * 1000));
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
			dcuInfoVO.setETypeTimeErrorLimit(dcu.getE_MTIME_ERROR_LIMIT());
			dcuInfoVO.setGTypeTimeErrorLimit(dcu.getG_MTIME_ERROR_LIMIT());
			dcuInfoVO.setEaTypeTimeErrorLimit(dcu.getEA_MTIME_ERROR_LIMIT());
			dcuInfoVO.setETypeTimePeriod(dcu.getEM_TIME_P());
			dcuInfoVO.setGTypeTimePeriod(dcu.getGM_TIME_P());
			dcuInfoVO.setEaTypeTimePeriod(dcu.getEAM_TIME_P());
			dcuInfoVO.setCpuReset(dcu.getCPU_RESET());

			dcuInfoVO.setPnid(dcu.getPNID());
			dcuInfoVO.setAcodeReadOnly(dcu.getACODE_RO());
			dcuInfoVO.setAcodeReadWrite(dcu.getACODE_RW());
			dcuInfoVO.setSnmpReadOnly(dcu.getSNMP_PW_RO());
			dcuInfoVO.setSnmpReadWrite(dcu.getSNMP_PW_RW());
			dcuInfoVO.setSysDcuDesc(dcu.getS_SYS_DESCR());
			dcuInfoVO.setSysDcuName(dcu.getS_SYS_NAME());
			dcuInfoVO.setSysDcuId(dcu.getS_DCU_ID());
			dcuInfoVO.setSysUpTime(dcu.getS_SYS_UP_TIME());
			dcuInfoVO.setSysSerial(dcu.getS_SYS_SERIAL());
			dcuInfoVO.setSysModel(dcu.getS_SYS_MODEL());
			dcuInfoVO.setSysObjectId(dcu.getS_SYS_OBJECT_ID());
			dcuInfoVO.setSysMac(dcu.getS_SYS_MAC());
			dcuInfoVO.setSysOsVersion(dcu.getS_SYS_OS_VERS());
			dcuInfoVO.setSysHardWareVersion(dcu.getS_SYS_HW_VERS());
			dcuInfoVO.setSysFirmWareVersion(dcu.getS_SYS_FW_VERS());
			dcuInfoVO.setSysMibVersion(dcu.getS_SYS_MIB_VERS());
			dcuInfoVO.setSysDownTime(dcu.getS_SYS_DOWN_TIME());
			dcuInfoVO.setSysConsoleMac(dcu.getS_SYS_CON_MAC());
			dcuInfoVO.setSysMibEncrypt(dcu.getS_SYS_MIB_ENCRYPT());
			dcuInfoVO.setSysMrAgentUpTime(dcu.getS_SYS_MR_AGENT_UP_TIME());
			dcuInfoVO.setSysSnmpAgentUpTime(dcu.getS_SYS_SNMP_AGENT_UP_TIME());
			dcuInfoVO.setSysWanCode(dcu.getS_WAN_CODE());
			dcuInfoVO.setSysCommCode(dcu.getS_COMM_CODE());
			dcuInfoVO.setSysDcuType(dcu.getS_DCU_TYPE());
			dcuInfoVO.setSysState(dcu.getS_SYS_STATE());
			dcuInfoVO.setSysUpBps(dcu.getS_SYS_UP_BPS());
			dcuInfoVO.setSysDownBps(dcu.getS_SYS_DN_BPS());
			dcuInfoVO.setSysCpuUsage(dcu.getS_SYS_CPU_USAGE());
			dcuInfoVO.setSysMemoryUsage(dcu.getS_SYS_MEM_USAGE());
			dcuInfoVO.setSysTempValue(dcu.getS_SYS_TEMP_VALUE());
			dcuInfoVO.setSysDcuCoverStatus(dcu.getS_DCU_COVER_STATUS());
			dcuInfoVO.setSysSecurityStatus(dcu.getS_SYS_SECURITY_STATUS());
			dcuInfoVO.setSysReset(dcu.getS_SYS_RESET());
			dcuInfoVO.setSysIpAddrInfo(dcu.getS_SYS_IP_ADDR_INFO());
			dcuInfoVO.setSysTrapRecvInfo(dcu.getS_SYS_TRAP_RECV_INFO());
			dcuInfoVO.setSysCpuThresh(dcu.getS_CPU_THRESH());
			dcuInfoVO.setSysMemoryThresh(dcu.getS_MEM_THRESH());
			dcuInfoVO.setSysTempThresh(dcu.getS_TEMP_THRESH());
			dcuInfoVO.setSysUpBpsThresh(dcu.getS_UP_BPS_THRESH());
			dcuInfoVO.setSysDownBpsThresh(dcu.getS_DOWN_BPS_THRESH());
			dcuInfoVO.setIsDelete(dcu.getIS_DELETE());
			dcuInfoVO.setWriteDate(new Date(dcu.getWDATE() * 1000));
			dcuInfoVO.setUpdateDate(new Date(dcu.getUDATE() * 1000));
			dcuInfoVO.setDcuStatus(dcu.getDSTATUS());

			dcuInfoVO.setLatitude(dcu.getLAT());
			dcuInfoVO.setLongitude(dcu.getLON());
			dcuInfoVO.setRouterIp(dcu.getROUTER_IP());

			dcuInfoVO.setMeterCount(count);

		}

		return dcuInfoVO;
	}

	@Override
	public int setDcuData(DcuRegVO dcuRegVO) throws Exception {

		DcuInfoEntity dcuInfoEntity = new DcuInfoEntity();

		dcuInfoEntity.setDID(dcuRegVO.getDcuId());
		dcuInfoEntity.setDCU_IP(dcuRegVO.getDcuIp());
		dcuInfoEntity.setDCU_PORT(dcuRegVO.getDcuPort());
		dcuInfoEntity.setROUTER_IP(dcuRegVO.getRouterIp());
		dcuInfoEntity.setLAT(dcuRegVO.getLatitude());
		dcuInfoEntity.setLON(dcuRegVO.getLongitude());
		dcuInfoEntity.setWDATE(new Date().getTime() / 1000);
		dcuInfoEntity.setUDATE(new Date().getTime() / 1000);

		dcuInfoEntity.setFEP_IP("");
		dcuInfoEntity.setFEP_PORT(0);
		dcuInfoEntity.setMAC_A("");
		dcuInfoEntity.setMAC_B("");
		dcuInfoEntity.setMAC_C("");
		dcuInfoEntity.setITIME(new Date().getTime() / 1000);
		dcuInfoEntity.setFWV("");
		dcuInfoEntity.setT_MASK("");
		dcuInfoEntity.setF_BUILD("");
		dcuInfoEntity.setDSTATUS("RET_FAIL_CONNECTION"); // 초기 등록시 설정
		dcuInfoEntity.setIS_DELETE("N"); // 초기 등록시 설정

		try {
			dcuInfoDAO.save(dcuInfoEntity);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public int setDcuDelete(String did) throws Exception {

		try {
			dcuInfoDAO.setDcuDelete(did);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public int setDcuIp(String dcuId, String dcuIp) throws Exception {

		try {
			dcuInfoDAO.setDcuIp(dcuId, dcuIp);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public int setDcuPort(String dcuId, int dcuPort) throws Exception {

		try {
			dcuInfoDAO.setDcuPort(dcuId, dcuPort);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public int setRouterIp(String dcuId, String routerIp) throws Exception {

		try {
			dcuInfoDAO.setRouterIp(dcuId, routerIp);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public int setLatLon(String dcuId, float lat, float lon) throws Exception {

		try {
			dcuInfoDAO.setLatLon(dcuId, lat, lon);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public ResultCountVO getMeterCount() throws Exception {
		ResultCountVO resultCountVO = new ResultCountVO();
		resultCountVO.setCount(meterInfoDAO.count());
		return resultCountVO;
	}

	@Override
	public List<MeterInfoListVO> getMeterListData(int gseq) throws Exception {

		List<MeterInfoInterfaceVO> meter = null;

		if (gseq == 0) {
//			meter = meterInfoDAO.getMeterList();
			meter = meterInfoDAO.getMeterNoMappList(); // 미매핑 정보 목록
		} else {
			meter = meterInfoDAO.getMeterList(gseq);
		}

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
			meterInfoListVO.setReadingDay(meter.get(i).getMrd());
			meterInfoListVO.setUpdateDate(new Date(meter.get(i).getUDate() * 1000));

			list.add(meterInfoListVO);
		}

		return list;
	}

	@Override
	public MeterInfoVO getMeterData(int gseq, String meterid) throws Exception {

		if (meterid.length() != 11) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "계량기 번호는 11자리입니다.");
		}

		MeterInfoEntity meter = meterInfoDAO.findByMETERID(meterid);

		if (meter == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "Meter ID 정보를 확인해주세요.");
		}

		EstateMeterInfoInterfaceVO data = meterInfoDAO.getEstateMeterInfo(gseq, meterid);

		RealTimeEntity lp = realTimeDAO.findByMETERID(meterid);

		MeterInfoVO meterInfoVO = new MeterInfoVO();

		meterInfoVO.setRegionSeq(data.getRSeq());
		meterInfoVO.setEstateSeq(data.getGSeq());
		meterInfoVO.setRegionName(data.getRName());
		meterInfoVO.setEstateId(data.getGId());
		meterInfoVO.setEstateName(data.getGName());
		meterInfoVO.setBuildingName(data.getBName());
		meterInfoVO.setHouseName(data.getHo());

		meterInfoVO.setLpTime(new Date(lp.getMTIME() * 1000));
		meterInfoVO.setLp(Float.valueOf(lp.getFAP()) / 1000);

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
	public int setMeterDelete(String meterid) throws Exception {

		try {
			meterInfoDAO.setMeterDelete(meterid);
			return 0;
		} catch (Exception e) {
			return 1;
		}

	}

	@Override
	public ResultCountVO getOtherMeterCount() throws Exception {
		ResultCountVO resultCountVO = new ResultCountVO();
		resultCountVO.setCount(deviceInfoDAO.count());
		return resultCountVO;
	}

	@Override
	public List<MeterOtherInfoListVO> getOtherMeterListData(int gseq) throws Exception {

		List<DeviceInfoEntity> data = deviceInfoDAO.findByGSEQ(gseq);

		DeviceEstateInterfaceVO name = deviceInfoDAO.getDeviceEstateInfo(gseq);

		List<MeterOtherInfoListVO> list = new ArrayList<MeterOtherInfoListVO>();
		MeterOtherInfoListVO meterOtherInfoListVO = new MeterOtherInfoListVO();

		for (DeviceInfoEntity device : data) {
			meterOtherInfoListVO = new MeterOtherInfoListVO();

			meterOtherInfoListVO.setRegionSeq(name.getRSEQ());
			meterOtherInfoListVO.setRegionName(name.getRNAME());

			meterOtherInfoListVO.setEstateSeq(name.getGSEQ());
			meterOtherInfoListVO.setEstateId(name.getGID());
			meterOtherInfoListVO.setEstateName(name.getGNAME());

			meterOtherInfoListVO.setBuildingName(device.getDONG());
			meterOtherInfoListVO.setHouseName(device.getHO());
			meterOtherInfoListVO.setGatewayId(device.getGATEWAYID());
			meterOtherInfoListVO.setMeterId(device.getMETERID());
			meterOtherInfoListVO.setMeterType(device.getTYPE()); // 계량기 타입 // 1:전기,2:가스,3:수도,4:온수,5:난방
			meterOtherInfoListVO.setUpdateDate(new Date(device.getWDATE() * 1000));

			list.add(meterOtherInfoListVO);
		}

		return list;
	}

	@Override
	public MeterOtherInfoVO getOtherMeterData(int gseq, String gatewayId, String meterId) throws Exception {

		DeviceInfoEntity data = deviceInfoDAO.findByGSEQAndGATEWAYIDAndMETERID(gseq, gatewayId, meterId);

		DeviceEstateInterfaceVO name = deviceInfoDAO.getDeviceEstateInfo(gseq);

		MeterOtherInfoVO meterOtherInfoVO = new MeterOtherInfoVO();

		meterOtherInfoVO.setRegionSeq(name.getRSEQ());
		meterOtherInfoVO.setRegionName(name.getRNAME());
		meterOtherInfoVO.setEstateSeq(name.getGSEQ());
		meterOtherInfoVO.setEstateId(name.getGID());
		meterOtherInfoVO.setEstateName(name.getGNAME());
		meterOtherInfoVO.setBuildingName(data.getDONG());
		meterOtherInfoVO.setHouseName(data.getHO());
		meterOtherInfoVO.setGatewayId(data.getGATEWAYID());
		meterOtherInfoVO.setMeterId(data.getMETERID());
		meterOtherInfoVO.setReadingType(data.getTYPE());
		meterOtherInfoVO.setMac(data.getMAC());
		meterOtherInfoVO.setDeviceName(data.getDEVICE_NAME());
		meterOtherInfoVO.setReadingDay(data.getMRD());
		meterOtherInfoVO.setLpPeriod(data.getLP_PERIOD());
		meterOtherInfoVO.setMeterTime(new Date());

		return meterOtherInfoVO;
	}

}
