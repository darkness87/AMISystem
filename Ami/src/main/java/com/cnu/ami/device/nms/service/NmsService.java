package com.cnu.ami.device.nms.service;

import java.util.List;

import com.cnu.ami.device.nms.models.MasterModemListVO;
import com.cnu.ami.device.nms.models.NmsDcuCheckListVO;
import com.cnu.ami.device.nms.models.NmsDcuListVO;

public interface NmsService {

	public List<NmsDcuListVO> getDcuList(int gseq) throws Exception;

	public boolean setDCURebootList(List<NmsDcuCheckListVO> nmsDcuCheckListVO) throws Exception;
	
	public List<MasterModemListVO> getModemMeterList(String dcuId) throws Exception;
	
	public boolean setDCUFirmwareList(List<NmsDcuCheckListVO> nmsDcuCheckListVO) throws Exception;
	
	public boolean setMODEMFirmwareList(List<NmsDcuCheckListVO> nmsDcuCheckListVO) throws Exception;

}
