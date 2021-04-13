package com.cnu.ami.device.building.service;

import java.util.List;

import com.cnu.ami.common.ResultCountVO;
import com.cnu.ami.common.ResultDataVO;
import com.cnu.ami.device.building.models.BuildingVO;
import com.cnu.ami.device.building.models.DcuSeqStatusVO;
import com.cnu.ami.device.building.models.DcuStatusVO;

public interface BuildingService {

	public ResultCountVO getBuildingCount() throws Exception;

	public BuildingVO getBulidingData(BuildingVO buildingVO) throws Exception;

	public List<BuildingVO> getBuildingListData(int rseq, int gseq) throws Exception;

	public int setBulidingData(BuildingVO buildingVO) throws Exception;

	public int setBulidingDcuData(BuildingVO buildingVO) throws Exception;

	public int getBuildNameCheck(int gseq, String buildingName) throws Exception;

	public int getBuildNameCheck(int bseq, int gseq, String buildingName) throws Exception;

	public DcuStatusVO getDcuIdCheck(String dcuId) throws Exception;

	public DcuStatusVO getDcuIdCheck(int bseq, String dcuId) throws Exception;

	public int setBuildingDelete(String dcuId, int bseq) throws Exception;

	public ResultDataVO<DcuSeqStatusVO> setDcuMappInsert(String dcuId, int bseq) throws Exception;

	public int setDcuMappDelete(String dcuId, int bseq) throws Exception;

}
