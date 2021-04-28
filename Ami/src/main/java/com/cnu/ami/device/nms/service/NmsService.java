package com.cnu.ami.device.nms.service;

import java.util.List;

import com.cnu.ami.device.nms.models.MasterModemListVO;
import com.cnu.ami.device.nms.models.NmsDcuListVO;
import com.cnu.ami.device.nms.models.NmsDcuRebootListVO;

public interface NmsService {

	public List<NmsDcuListVO> getDcuList(int gseq) throws Exception;

	public boolean setDCURebootList(List<NmsDcuRebootListVO> nmsDcuRebootListVO) throws Exception;
	
	public List<MasterModemListVO> getModemMeterList(String dcuId) throws Exception;

}
