package com.cnu.ami.device.equipment.service;

import java.util.List;

import com.cnu.ami.common.ResultCountVO;
import com.cnu.ami.device.equipment.models.DcuInfoListVO;
import com.cnu.ami.device.equipment.models.DcuInfoVO;
import com.cnu.ami.device.equipment.models.DcuRegVO;
import com.cnu.ami.device.equipment.models.MeterInfoListVO;
import com.cnu.ami.device.equipment.models.MeterInfoVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoListVO;
import com.cnu.ami.device.equipment.models.MeterOtherInfoVO;

public interface EquipmentService {

	public ResultCountVO getDcuCount() throws Exception;

	public List<DcuInfoListVO> getDcuListData(int gseq) throws Exception;

	public DcuInfoVO getDcuData(String did) throws Exception;

	public int setDcuData(DcuRegVO dcuRegVO) throws Exception;

	public int setDcuDelete(String did) throws Exception;

	public ResultCountVO getMeterCount() throws Exception;

	public List<MeterInfoListVO> getMeterListData(int gseq) throws Exception;

	public MeterInfoVO getMeterData(String meterid) throws Exception;

	public int setMeterDelete(String meterid) throws Exception;

	public ResultCountVO getOtherMeterCount() throws Exception;

	public List<MeterOtherInfoListVO> getOtherMeterListData(int gseq) throws Exception;

	public MeterOtherInfoVO getOtherMeterData(int gseq, String gatewayId, String meterId) throws Exception;

}
