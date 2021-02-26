package com.cnu.ami.metering.menu1.service;

import java.util.List;

import com.cnu.ami.metering.menu1.models.Menu1MeterLpVO;
import com.cnu.ami.metering.menu1.models.Menu1VO;
import com.cnu.ami.metering.menu1.models.ModelLpTestVO;

public interface Menu1Service {

	public Object testReadData() throws Exception;

	public List<Menu1VO> testSelectData() throws Exception;

	public int testInsertData(String meterid, float lp) throws Exception;

	public List<Menu1MeterLpVO> testjoinData() throws Exception;

	public List<ModelLpTestVO> testjoinLpData() throws Exception;

}
