package com.cnu.ami.dashboard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.dashboard.dao.DashBoardDAO;
import com.cnu.ami.dashboard.models.DashBoardVO;
import com.cnu.ami.dashboard.service.DashBoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DashBoardServiceImpl implements DashBoardService {

	@Autowired
	private DashBoardDAO dashBoardDAO;

	public Object testReadData() throws Exception {

		log.info("{}", dashBoardDAO.getOne((long) 25));

		DashBoardVO test = (DashBoardVO) dashBoardDAO.getOne((long) 25);

		log.info("{}", test.getId());
		log.info("{}", test.getMeterid());

		DashBoardVO dashBoardVO = new DashBoardVO();

		dashBoardVO.setId(test.getId());
		dashBoardVO.setMeterid(test.getMeterid());

		return dashBoardVO;
	}

	public List<DashBoardVO> testSelectData() throws Exception {

		List<DashBoardVO> test = dashBoardDAO.findById(25);

		log.info("testData : {}", test);

		return test;
	}

}
