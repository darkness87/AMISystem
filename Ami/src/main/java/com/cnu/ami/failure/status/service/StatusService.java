package com.cnu.ami.failure.status.service;

import java.util.List;

import com.cnu.ami.failure.status.models.DcuFailureStatusVO;

public interface StatusService {

	public List<DcuFailureStatusVO> getDcuStatus(int gseq) throws Exception;
}
