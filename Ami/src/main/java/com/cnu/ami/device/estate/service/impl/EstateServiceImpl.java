package com.cnu.ami.device.estate.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.estate.models.EstateVO;
import com.cnu.ami.device.estate.service.EstateService;

@Service
public class EstateServiceImpl implements EstateService {

	@Autowired
	private EstateDAO estateDAO;

	public List<EstateVO> getEstateListData() throws Exception {

		List<EstateEntity> data = estateDAO.findAll();

		List<EstateVO> list = new ArrayList<EstateVO>();

		EstateVO estateVO = new EstateVO();

		for (int i = 0; data.size() > i; i++) {
			estateVO = new EstateVO();

			list.add(estateVO);
		}

		return list;
	}

	@Override
	public EstateVO getEstateData(String esateId) throws Exception {

//		EstateEntity data = estateDAO.findByEstateId(esateId);

		EstateVO estateVO = new EstateVO();


		return estateVO;
	}

}
