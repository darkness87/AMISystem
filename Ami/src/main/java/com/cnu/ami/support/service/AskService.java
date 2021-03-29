package com.cnu.ami.support.service;

import java.util.List;

import com.cnu.ami.support.models.AskListVO;
import com.cnu.ami.support.models.AskVO;

public interface AskService {

	public AskVO getAskData(long aseq) throws Exception;

	public List<AskListVO> getAskListData(int gseq) throws Exception;

}
