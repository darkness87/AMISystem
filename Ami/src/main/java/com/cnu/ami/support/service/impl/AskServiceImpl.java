package com.cnu.ami.support.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.support.dao.AskDAO;
import com.cnu.ami.support.models.AskVO;
import com.cnu.ami.support.service.AskService;

@Service
public class AskServiceImpl implements AskService {

	@Autowired
	private AskDAO askDAO;

	@Override
	public AskVO getAskData() throws Exception {
		
		return null;
	}

	@Override
	public List<AskVO> getAskListData() throws Exception {
		askDAO.findAll();
		return null;
	}

}
