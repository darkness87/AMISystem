package com.cnu.ami.metering.menu2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.metering.menu2.dao.Menu2DAO;
import com.cnu.ami.metering.menu2.models.Menu2VO;
import com.cnu.ami.metering.menu2.service.Menu2Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Menu2ServiceImpl implements Menu2Service {

	@Autowired
	private Menu2DAO menu2DAO;

	public Object testReadData() throws Exception {

		log.info("{}", menu2DAO.getOne((long) 25));

		Menu2VO test = (Menu2VO) menu2DAO.getOne((long) 25);

		log.info("{}", test.getId());
		log.info("{}", test.getMeterid());

		Menu2VO menu2VO = new Menu2VO();

		menu2VO.setId(test.getId());
		menu2VO.setMeterid(test.getMeterid());

		return menu2VO;
	}

	public List<Menu2VO> testSelectData() throws Exception {

		List<Menu2VO> test = menu2DAO.findById(25);

		log.info("testData : {}", test);

		return test;
	}

}
