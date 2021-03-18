package com.cnu.ami.device.equipment.service;

import java.util.List;

import com.cnu.ami.device.equipment.models.DcuInfoListVO;
import com.cnu.ami.device.equipment.models.DcuInfoVO;
import com.cnu.ami.device.equipment.models.DcuRegVO;
import com.cnu.ami.device.equipment.models.MeterInfoListVO;
import com.cnu.ami.device.equipment.models.MeterInfoVO;

public interface EquipmentService {

	public List<DcuInfoListVO> getDcuListData(int gseq) throws Exception;

	public DcuInfoVO getDcuData(String did) throws Exception;

	public int setDcuData(DcuRegVO dcuRegVO) throws Exception;

	public List<MeterInfoListVO> getMeterListData(int gseq) throws Exception;

	public MeterInfoVO getMeterData(String meterid) throws Exception;

}
