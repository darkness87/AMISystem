package com.cnu.ami.support.service;

import java.util.List;

import com.cnu.ami.support.models.AskVO;

public interface AskService {

	public AskVO getAskData() throws Exception;

	public List<AskVO> getAskListData() throws Exception;

}
