package com.cnu.ami.dashboard.service.impl;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.dashboard.dao.DashDcuInfoDAO;
import com.cnu.ami.dashboard.dao.DashMeterInfoDAO;
import com.cnu.ami.dashboard.dao.DashModemInfoDAO;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllListVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateSubVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllListVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.WeatherVO;
import com.cnu.ami.dashboard.service.DashBoardService;
import com.sun.management.OperatingSystemMXBean;

@Service
public class DashBoardServiceImpl implements DashBoardService {
	
	@Autowired
	private DashDcuInfoDAO dashDcuInfoDAO;
	
	@Autowired
	private DashMeterInfoDAO dashMeterInfoDAO;
	
	@Autowired
	private DashModemInfoDAO dashModemInfoDAO;

	@Override
	public UseDayHourAllVO getElectricUseDayHourAll() throws Exception {

		UseDayHourAllVO useDayHourAllVO = new UseDayHourAllVO();

		useDayHourAllVO.setUseAll(123456.789f);
		useDayHourAllVO.setDate(new Date());
		useDayHourAllVO.setType("electric , 임시값");

		// DB에서 실 데이터 가져와야함
		List<UseDayHourAllListVO> list = new ArrayList<UseDayHourAllListVO>();
		UseDayHourAllListVO useDayHourAllListVO = new UseDayHourAllListVO();

		for (int i = 1; i < 25; i++) {
			useDayHourAllListVO = new UseDayHourAllListVO();
			useDayHourAllListVO.setTime(i);
			useDayHourAllListVO.setUse(789.123f);

			list.add(useDayHourAllListVO);
		}

		useDayHourAllVO.setArrayData(list);

		return useDayHourAllVO;
	}

	@Override
	public RateVO getElectricMeterReadingRateDayAll() throws Exception {

		RateVO rateVO = new RateVO();
		RateSubVO rate = new RateSubVO();

		rateVO.setMeterReadingRate(99.9f);
		rateVO.setResponsibility(100f);

		rate.setTodayRate(99.9f);
		rate.setYesterdayRate(99.9f);

		rateVO.setRate(rate);

		return rateVO;
	}

	@Override
	public FailureAllVO getElectricFailureDayHourAll() throws Exception {

		FailureAllVO failureAllVO = new FailureAllVO();

		failureAllVO.setFailureTodayCount(10);
		failureAllVO.setDate(new Date());
		failureAllVO.setType("electric , 장애임시값");

		// DB에서 실 데이터 가져와야함
		List<FailureAllListVO> list = new ArrayList<FailureAllListVO>();
		FailureAllListVO failureAllListVO = new FailureAllListVO();

		for (int i = 1; i < 25; i++) {
			failureAllListVO = new FailureAllListVO();
			failureAllListVO.setTime(i);
			failureAllListVO.setCount(10);

			list.add(failureAllListVO);
		}

		failureAllVO.setArrayData(list);

		return failureAllVO;
	}

	@Override
	public WeatherVO getWeatherRealtimeAll() throws Exception {

		WeatherVO weatherVO = new WeatherVO();

		weatherVO.setTemperature(10);
		weatherVO.setMaxTemperature(13);
		weatherVO.setMinTemperature(3);
		weatherVO.setLocation("성남시");
		weatherVO.setCodeValue(0); // 0:맑음, 1:약간흐림, 2:흐림, 3:비, 4:눈, 5:천둥/번개 => 재확인후 결정
		weatherVO.setDate(new Date());
		weatherVO.setDescribe("날씨 : 맑음");

		return weatherVO;
	}

	@Override
	public WeatherVO getWeatherDataWeatherAll() throws Exception {

		WeatherVO weatherVO = new WeatherVO();

		weatherVO.setLocation("성남시");
		weatherVO.setCodeValue(0); // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
		weatherVO.setDate(new Date());
		weatherVO.setDescribe("데이터 날씨 : 아주 좋아요");

		return weatherVO;

	}

	@Override
	public List<DashBoardMapVO> getLocationFailureMapInfo() throws Exception {

		// TODO key,value 형식으로 넘겨야 될듯 - 대괄호 안에 대괄호 - 하이차트맵 샘플 참고

		List<DashBoardMapVO> dashmap = new ArrayList<DashBoardMapVO>();
		DashBoardMapVO dashBoardMapVO = new DashBoardMapVO();

		for (int i = 0; i < 3; i++) {
			dashBoardMapVO = new DashBoardMapVO();
			if (i == 0) {
				dashBoardMapVO.setCode("kr-kg");
				dashBoardMapVO.setValue(5);
			} else if (i == 1) {
				dashBoardMapVO.setCode("kr-in");
				dashBoardMapVO.setValue(7);
			} else if (i == 2) {
				dashBoardMapVO.setCode("kr-so");
				dashBoardMapVO.setValue(10);
			} else {
				dashBoardMapVO.setCode("kr-cb");
				dashBoardMapVO.setValue(3);
			}

			dashmap.add(dashBoardMapVO);
		}

		return dashmap;
	}

	@SuppressWarnings("restriction")
	@Override
	public ServerManagementVO getServerManagementInfo() throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ServerManagementVO serverManagementVO = new ServerManagementVO();

		// JVM memory
		float gb = 1024 * 1024 * 1024;
		long heapSize = Runtime.getRuntime().totalMemory();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		long heapUseSize = heapSize - heapFreeSize;

		// OS
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

		serverManagementVO.setDate(dateFormat.format(date));
		serverManagementVO.setOsCpu(String.format("%.3f", osBean.getSystemCpuLoad() * 100));
		serverManagementVO.setOsMemory(
				String.format("%.3f", ((osBean.getTotalPhysicalMemorySize() - osBean.getFreePhysicalMemorySize()) / gb)
						/ (osBean.getTotalPhysicalMemorySize() / gb) * 100));
		serverManagementVO.setJvmUsed(String.format("%.3f", (double) heapUseSize / gb));
		serverManagementVO.setJvmFree(String.format("%.3f", (double) heapFreeSize / gb));
		serverManagementVO.setJvmTotal(String.format("%.3f", (double) heapSize / gb));
		serverManagementVO.setJvmMax(String.format("%.3f", (double) heapMaxSize / gb));

		return serverManagementVO;
	}

	@Override
	public List<DeviceRegVO> getElectricRegistrationDevice() throws Exception {

		// TODO 임의 테스트 코드 - 실DB데이터 카운트하여 재수정한다.

		List<DeviceRegVO> list = new ArrayList<DeviceRegVO>();

		DeviceRegVO deviceRegVO = new DeviceRegVO();

		// DCU
		deviceRegVO.setDeviceName("DCU");
		deviceRegVO.setDeviceRegConut((int) dashDcuInfoDAO.count());
		deviceRegVO.setType("electric");
		list.add(deviceRegVO);

		deviceRegVO = new DeviceRegVO();
		// Modem
		deviceRegVO.setDeviceName("Modem");
		deviceRegVO.setDeviceRegConut((int) dashModemInfoDAO.count());
		deviceRegVO.setType("electric");
		list.add(deviceRegVO);

		deviceRegVO = new DeviceRegVO();
		// Meter
		deviceRegVO.setDeviceName("Meter");
		deviceRegVO.setDeviceRegConut((int) dashMeterInfoDAO.count());
		deviceRegVO.setType("electric");
		list.add(deviceRegVO);

		return list;
	}

	@Override
	public Object getLocationUseList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
