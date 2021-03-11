package com.cnu.ami.search.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.search.dao.SearchEstateDAO;
import com.cnu.ami.search.dao.SearchRegionDAO;
import com.cnu.ami.search.dao.entity.RegionEntity;
import com.cnu.ami.search.models.EstateVO;
import com.cnu.ami.search.models.RegionVO;
import com.cnu.ami.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	SearchRegionDAO searchRegionDAO;

	@Autowired
	SearchEstateDAO searchEstateDAO;

	@Override
	public List<RegionVO> getRegionList() throws Exception {

		List<RegionEntity> data = searchRegionDAO.findAll();

		List<RegionVO> list = new ArrayList<RegionVO>();
		RegionVO regionVO = new RegionVO();

		regionVO.setRegionSeq(0);
		regionVO.setRegionName("전체");
		list.add(regionVO);

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
			estateVO.setGSeq(data.get(i).getGSeq());
			estateVO.setRSeq(data.get(i).getRSeq());
			estateVO.setGId(data.get(i).getGId());
			estateVO.setGName(data.get(i).getGName());
			list.add(estateVO);
		}

		return list;
	}

}
