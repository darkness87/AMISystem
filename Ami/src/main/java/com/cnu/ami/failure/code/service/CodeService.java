package com.cnu.ami.failure.code.service;

import java.util.List;

import com.cnu.ami.failure.code.models.CodeValueVO;

public interface CodeService {

	public List<CodeValueVO> getDataList(int gseq, String dcuId, String fromDate, String toDate, int statusCode)
			throws Exception;

	public List<CodeValueVO> getStatusDataList(int gseq, long statusCode) throws Exception;

}
