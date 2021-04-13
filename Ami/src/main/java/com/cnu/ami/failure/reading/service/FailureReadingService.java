package com.cnu.ami.failure.reading.service;

import java.util.List;

import com.cnu.ami.failure.reading.models.FailureReadingVO;

public interface FailureReadingService {

	public List<FailureReadingVO> getFailureReadingData(int gseq) throws Exception;

}
