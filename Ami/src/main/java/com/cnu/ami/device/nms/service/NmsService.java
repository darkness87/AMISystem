package com.cnu.ami.device.nms.service;

import java.util.List;

import com.cnu.ami.device.nms.models.NmsDcuListVO;

public interface NmsService {

	public List<NmsDcuListVO> getDcuList(int gseq);
}
