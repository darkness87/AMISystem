package com.cnu.ami.search.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.device.building.dao.entity.BuildingEntity;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.search.dao.SearchBuildingDAO;
import com.cnu.ami.search.dao.SearchDcuDAO;
import com.cnu.ami.search.dao.SearchEstateDAO;
import com.cnu.ami.search.dao.SearchRegionDAO;
import com.cnu.ami.search.dao.entity.DcuMappEntity;
import com.cnu.ami.search.dao.entity.RegionEntity;
import com.cnu.ami.search.models.BuildingVO;
import com.cnu.ami.search.models.DcuMappVO;
import com.cnu.ami.search.models.EstateVO;
import com.cnu.ami.search.models.RegionVO;
import com.cnu.ami.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	SearchRegionDAO searchRegionDAO;

	@Autowired
	SearchEstateDAO searchEstateDAO;

	@Autowired
	SearchBuildingDAO searchBuildingDAO;

	@Autowired
	SearchDcuDAO searchDcuDAO;

	@Override
	public List<RegionVO> getRegionList() throws Exception {

		List<RegionEntity> data = searchRegionDAO.findAll();

		List<RegionVO> list = new ArrayList<RegionVO>();
		RegionVO regionVO = new RegionVO();

		for (int i = 0; data.size() > i; i++) {
			regionVO = new RegionVO();
			regionVO.setRegionSeq(data.get(i).getRSeq());
			regionVO.setRegionName(data.get(i).getRName());
			list.add(regionVO);
		}

		return list;
	}

	@Override
	public List<EstateVO> getEstateList(int region) throws Exception {

		List<EstateEntity> data = new ArrayList<EstateEntity>();

		if (region == 0) { // 전체
			data = searchEstateDAO.findAll();
		} else {
			data = searchEstateDAO.findByrSeq(region);
		}

		List<EstateVO> list = new ArrayList<EstateVO>();
		EstateVO estateVO = new EstateVO();

		for (int i = 0; data.size() > i; i++) {
			estateVO = new EstateVO();
			estateVO.setEstateSeq(data.get(i).getGSeq());
			estateVO.setRegionSeq(data.get(i).getRSeq());
			estateVO.setEstateId(data.get(i).getGId());
			estateVO.setEstateName(data.get(i).getGName());
			list.add(estateVO);
		}

		return list;
	}

	@Override
	public List<BuildingVO> getBuildingList(int gseq) throws Exception {

		List<BuildingEntity> data = new ArrayList<BuildingEntity>();

		if (gseq == 0) { // 전체
			data = searchBuildingDAO.findAll();
		} else {
			data = searchBuildingDAO.findBygSeq(gseq);
		}

		List<BuildingVO> list = new ArrayList<BuildingVO>();
		BuildingVO buildingVO = new BuildingVO();

		for (int i = 0; data.size() > i; i++) {
			buildingVO = new BuildingVO();

			buildingVO.setBuildingSeq(data.get(i).getBSeq());
			buildingVO.setBuildingName(data.get(i).getBNAME());

			list.add(buildingVO);
		}

		return list;
	}

	@Override
	public List<DcuMappVO> getDcuList(int bseq) throws Exception {

		List<DcuMappEntity> data = new ArrayList<DcuMappEntity>();

		if (bseq == 0) { // 전체
			data = searchDcuDAO.findAll();
		} else {
			data = searchDcuDAO.findBybSeq(bseq);
		}

		List<DcuMappVO> list = new ArrayList<DcuMappVO>();
		DcuMappVO dcuMappVO = new DcuMappVO();

		for (int i = 0; data.size() > i; i++) {
			dcuMappVO = new DcuMappVO();

			dcuMappVO.setDcuId(data.get(i).getDId());

			list.add(dcuMappVO);
		}

		return list;
	}

}
