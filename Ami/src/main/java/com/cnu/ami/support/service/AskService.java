package com.cnu.ami.support.service;

import java.util.List;

import com.cnu.ami.support.models.AskListVO;
import com.cnu.ami.support.models.AskSetVO;
import com.cnu.ami.support.models.AskUpdateVO;
import com.cnu.ami.support.models.AskVO;

public interface AskService {

	public AskVO getAskData(long aseq) throws Exception;

	public List<AskListVO> getAskListData(int gseq, String toDate, String fromDate, String userId) throws Exception;

	public int setAskData(AskSetVO askSetVO) throws Exception;

	public int updateAskData(AskUpdateVO askUpdateVO) throws Exception;

}
