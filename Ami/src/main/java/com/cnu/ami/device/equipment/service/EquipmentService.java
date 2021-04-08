package com.cnu.ami.device.equipment.service;

import java.util.List;

import com.cnu.ami.device.equipment.models.DcuInfoListVO;
import com.cnu.ami.device.equipment.models.DcuInfoVO;
import com.cnu.ami.device.equipment.models.DcuRegVO;
import com.cnu.ami.device.equipment.models.MeterInfoListVO;
import com.cnu.ami.device.equipment.models.MeterInfoVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoListVO;

public interface EquipmentService {

	public List<DcuInfoListVO> getDcuListData(int gseq) throws Exception;

	public DcuInfoVO getDcuData(String did) throws Exception;

	public int setDcuData(DcuRegVO dcuRegVO) throws Exception;
	
	public int setDcuDelete(String did) throws Exception;

	public List<MeterInfoListVO> getMeterListData(int gseq) throws Exception;

	public MeterInfoVO getMeterData(String meterid) throws Exception;

	public List<MeterOtherInfoListVO> getOtherMeterListData(int gseq, int meterType) throws Exception;
	
	public MeterOtherInfoListVO getOtherMeterData(String meterId, int meterType) throws Exception;

}
