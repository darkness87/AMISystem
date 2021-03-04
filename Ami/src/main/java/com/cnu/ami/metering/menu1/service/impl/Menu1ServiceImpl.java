package com.cnu.ami.metering.menu1.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.metering.menu1.dao.Menu1DAO;
import com.cnu.ami.metering.menu1.dao.Menu1JoinDAO;
import com.cnu.ami.metering.menu1.dao.LpTestInfoDAO;
import com.cnu.ami.metering.menu1.dao.LpTestJoinDAO;
import com.cnu.ami.metering.menu1.models.Menu1MeterLpVO;
import com.cnu.ami.metering.menu1.models.Menu1VO;
import com.cnu.ami.metering.menu1.models.ModelLpTestVO;
import com.cnu.ami.metering.menu1.models.lpTestDataVO;
import com.cnu.ami.metering.menu1.models.lpTestInfoVO;
import com.cnu.ami.metering.menu1.service.Menu1Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Menu1ServiceImpl implements Menu1Service {

	@Autowired
	private Menu1DAO menu1DAO;

	@Autowired
	private Menu1JoinDAO menu1JoinDAO;

	@Autowired
	private LpTestJoinDAO lpTestJoinDAO;

	@Autowired
	private LpTestInfoDAO lpTestInfoDAO;

	public Object testReadData() throws Exception {

		Menu1VO test = (Menu1VO) menu1DAO.getOne((long) 25);

		Menu1VO menu1VO = new Menu1VO();

		menu1VO.setId(test.getId());
		menu1VO.setMeterid(test.getMeterid());

		return menu1VO;
	}

	public List<Menu1VO> testSelectData() throws Exception {

		List<Menu1VO> test = menu1DAO.findById(25);

		return test;
	}

	public int testInsertData(String meterid, float lp) throws Exception {

		Menu1VO menu1VO = new Menu1VO();
		Date date = new Date();

		menu1VO.setMeterid(meterid);
		menu1VO.setLp(lp);
		menu1VO.setDate(date);
		menu1VO.setInfo("create");

		try {
			menu1DAO.save(menu1VO);
		} catch (Exception e) {
			return 2;
		}

		return 1;
	}

	@Override
	public List<Menu1MeterLpVO> testjoinData() throws Exception {

		List<Menu1MeterLpVO> data = menu1JoinDAO.findAll();

		return data;
	}

	@Override
	public List<ModelLpTestVO> testjoinLpData() throws Exception {

		List<ModelLpTestVO> data = new ArrayList<ModelLpTestVO>();
		ModelLpTestVO modelLpTestVO = new ModelLpTestVO();

		List<lpTestDataVO> finddata_1 = lpTestJoinDAO.findAll();
		List<lpTestInfoVO> finddata_2 = lpTestInfoDAO.findAll();
		List<lpTestInfoVO> finddata_3 = lpTestInfoDAO.findByMeterid("06290000123");

		for (int i = 0; finddata_1.size() > i; i++) {
			modelLpTestVO = new ModelLpTestVO();

			modelLpTestVO.setId(finddata_1.get(i).getId());
			modelLpTestVO.setMeterid(finddata_1.get(i).getMeterid());
			modelLpTestVO.setDate(finddata_1.get(i).getDate());
			modelLpTestVO.setLp(finddata_1.get(i).getLp());

			for (int k = 0; finddata_2.size() > k; k++) {

				if (finddata_1.get(i).getMeterid().equals(finddata_2.get(k).getMeterid())) {
					modelLpTestVO.setCode(finddata_2.get(k).getCode());
					modelLpTestVO.setName(finddata_2.get(k).getName());

					data.add(modelLpTestVO);
				} else {
					log.info("매핑할 수 있는 정보가 없습니다.");
				}

			}

		}

		return data;
	}
	
	public List<Menu1VO> testLimitData(String meterid) throws Exception {

		List<Menu1VO> test = menu1DAO.findFirst2ByMeterid(meterid);

		return test;
	}

}
