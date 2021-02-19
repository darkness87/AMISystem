package com.cnu.ami.metering.menu1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.metering.menu1.dao.Menu1DAO;
import com.cnu.ami.metering.menu1.models.Menu1VO;
import com.cnu.ami.metering.menu1.service.Menu1Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Menu1ServiceImpl implements Menu1Service {

	@Autowired
	private Menu1DAO menu1DAO;

	public Object testReadData() throws Exception {

		log.info("{}", menu1DAO.getOne((long) 25));

		Menu1VO test = (Menu1VO) menu1DAO.getOne((long) 25);

		log.info("{}", test.getId());
		log.info("{}", test.getMeterid());

		Menu1VO menu1VO = new Menu1VO();

		menu1VO.setId(test.getId());
		menu1VO.setMeterid(test.getMeterid());

		return menu1VO;
	}

	public List<Menu1VO> testSelectData() throws Exception {

		List<Menu1VO> test = menu1DAO.findById(25);

		log.info("testData : {}", test);

		return test;
	}

}
