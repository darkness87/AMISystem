package com.cnu.ami.dashboard.service.impl;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.CnuAggregationOperation;
import com.cnu.ami.common.CollectionNameFormat;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.dashboard.dao.document.DayRateTemp;
import com.cnu.ami.dashboard.dao.document.UseDayHourTemp;
import com.cnu.ami.dashboard.dao.entity.RegionNameIneterfaceVO;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllListVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllListVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.WeatherVO;
import com.cnu.ami.dashboard.service.DashBoardService;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.MeterInfoDAO;
import com.cnu.ami.device.equipment.dao.ModemInfoDAO;
import com.cnu.ami.device.server.dao.ServerDAO;
import com.cnu.ami.device.server.dao.entity.ServerRegionIneterfaceVO;
import com.cnu.ami.scheduler.dao.WeatherDAO;
import com.cnu.ami.scheduler.dao.entity.WeatherEntity;
import com.cnu.ami.search.dao.SearchRegionDAO;
import com.sun.management.OperatingSystemMXBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DashBoardServiceImpl implements DashBoardService {

	@Autowired
	private DcuInfoDAO dcuInfoDAO;

	@Autowired
	private MeterInfoDAO meterInfoDAO;

	@Autowired
	private ModemInfoDAO modemInfoDAO;

	@Autowired
	private ServerDAO serverDAO;

	@Autowired
	private WeatherDAO weatherDAO;

	@Autowired
	private SearchRegionDAO searchRegionDAO;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public ResponseVO<UseDayHourAllVO> getElectricUseDayHourAll(HttpServletRequest request) throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());

		cal.add(Calendar.DATE, -1);

		String yesterday = dateFormat.format(cal.getTime());

		log.info("{},{}", today, yesterday);

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatDcu(today);

		String[] jsonRawString = { String.format("{ $match: { '$or':[{'day':'%s'},{'day':'%s'}] } }", today, yesterday)
//				  String.format("{ $match: { 'day':'%s' } }", today)
				, String.format("{ $unwind: { path: '$mids' } }"),
				String.format("{ $unwind: { path: '$mids.v',includeArrayIndex:'hour' } }"),
				"{ $group: { _id: {'day':'$day','hour':'$hour'} ,sum:{ '$sum':'$mids.v' } } }",
				"{ $project: { day: '$_id.day', hour: '$_id.hour', sum: '$sum' } }",
				"{ $sort: { '_id.day':1, '_id.hour':1 } }" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])),
				new CnuAggregationOperation(Document.parse(jsonRawString[4])),
				new CnuAggregationOperation(Document.parse(jsonRawString[5])));

		AggregationResults<UseDayHourTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				UseDayHourTemp.class);

//		log.info("{}", result.getRawResults());

		List<UseDayHourTemp> data = result.getMappedResults();

//		log.info("size : {}", data.size());

		UseDayHourAllVO useDayHourAllVO = new UseDayHourAllVO();

		List<UseDayHourAllListVO> todaylist = new ArrayList<UseDayHourAllListVO>();
		List<UseDayHourAllListVO> yesterdaylist = new ArrayList<UseDayHourAllListVO>();
		UseDayHourAllListVO useDayHourAllListVO = new UseDayHourAllListVO();

		for (int i = 0; i < data.size(); i++) {

			if (i == 24 || i == 49) { // 중간 사이값 삭제 // 전일에서 금일 사이 중복 값
//				log.info("i continue : {}", i);
				continue;
			}

//			log.info("i : {}, {}, {}", i, data.get(i).getHour(), data.get(i).getSum());

			useDayHourAllListVO = new UseDayHourAllListVO();

			if (data.get(i).getDay().equals(today)) {
				useDayHourAllListVO.setTime(data.get(i).getHour());
				if (data.get(i + 1).getSum() == 0) {
					useDayHourAllListVO.setUse(0);
				} else {
					useDayHourAllListVO.setUse(data.get(i + 1).getSum() - data.get(i).getSum());
				}

				todaylist.add(useDayHourAllListVO);
			} else if (data.get(i).getDay().equals(yesterday)) {
				useDayHourAllListVO.setTime(data.get(i).getHour());
				if (data.get(i + 1).getSum() == 0) {
					useDayHourAllListVO.setUse(0);
				} else {
					useDayHourAllListVO.setUse(data.get(i + 1).getSum() - data.get(i).getSum());
				}

				yesterdaylist.add(useDayHourAllListVO);
			}
		}

		int sumUse = 0;
		for (UseDayHourAllListVO sum : todaylist) {
			sumUse = sum.getUse() + sumUse;
		}

		useDayHourAllVO.setTodayUseAll(sumUse);
		useDayHourAllVO.setDay(today);
		useDayHourAllVO.setType("electric");

		useDayHourAllVO.setTodayData(todaylist);
		useDayHourAllVO.setYesterdayData(yesterdaylist);

		return new ResponseVO<UseDayHourAllVO>(request, useDayHourAllVO);
	}

	@Override
	public ResponseVO<RateVO> getElectricMeterReadingRateDayAll(HttpServletRequest request) throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());

		cal.add(Calendar.DATE, -1);

		String yesterday = dateFormat.format(cal.getTime());

		log.info("{},{}", today, yesterday);

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatDcu(today);

		String[] jsonRawString = {
				String.format("{$match: { $or: [ { day: '%s' }, { day: '%s' } ] }}", today, yesterday),
				"{$group: { _id: '$day', on: { $sum: '$cntOn' }, lp: { $sum: '$cntLp' }, total: { $sum: '$cntTotal' } }}",
				"{$sort: { _id: 1 }}" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])));

		AggregationResults<DayRateTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				DayRateTemp.class);

//		log.info("{}", result.getRawResults());

		List<DayRateTemp> data = result.getMappedResults();

		RateVO rateVO = new RateVO();

		for (DayRateTemp rate : data) {
			if (rate.get_id().equals(today)) {
				rateVO.setTodayMeterReadingRate((Float.valueOf(rate.getLp()) / rate.getTotal()) * 100f);
				rateVO.setTodayTimelyRate((Float.valueOf(rate.getOn()) / rate.getTotal()) * 100f);
			} else if (rate.get_id().equals(yesterday)) {
				rateVO.setYesterdayMeterReadingRate((Float.valueOf(rate.getLp()) / rate.getTotal()) * 100f);
				rateVO.setYesterdayTimelyRate((Float.valueOf(rate.getOn()) / rate.getTotal()) * 100f);
			}
		}

		return new ResponseVO<RateVO>(request, rateVO);
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

		ServerRegionIneterfaceVO region = serverDAO.findBySSEQ(1); // WAS/WEB 서버 SSEQ : 1

		log.info("{}", region.getRSEQ());

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String today = dateFormat.format(cal.getTime());

		WeatherEntity data = weatherDAO.findFirstByRSEQAndFCSTDATEOrderByFCSTTIMEDesc(region.getRSEQ(), today);

		RegionNameIneterfaceVO regionName = searchRegionDAO.findByrSeq(region.getRSEQ());

		WeatherVO weatherVO = new WeatherVO();

		weatherVO.setTemperature(data.getT1H());
		weatherVO.setLocation(regionName.getrName());
		weatherVO.setCodeValue(data.getSKY()); // 0:맑음, 1:약간흐림, 2:흐림, 3:비, 4:눈, 5:천둥/번개 => 재확인후 결정

		cal.set(Integer.valueOf(data.getFCSTDATE().substring(0, 4)),
				Integer.valueOf(data.getFCSTDATE().substring(4, 6)) - 1,
				Integer.valueOf(data.getFCSTDATE().substring(6, 8)),
				Integer.valueOf(data.getFCSTTIME().substring(0, 2)),
				Integer.valueOf(data.getFCSTTIME().substring(2, 4)), 0);

		weatherVO.setDate(cal.getTime());

		return weatherVO;
	}

	@Override
	public WeatherVO getWeatherDataWeatherAll() throws Exception {

		WeatherVO weatherVO = new WeatherVO();

		weatherVO.setLocation("성남시");
		weatherVO.setCodeValue(0); // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
		weatherVO.setDate(new Date());

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
	public ResponseVO<ServerManagementVO> getServerManagementInfo(HttpServletRequest request) throws Exception {

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

		return new ResponseVO<ServerManagementVO>(request, serverManagementVO);
	}

	@Override
	public List<DeviceRegVO> getElectricRegistrationDevice() throws Exception {

		List<DeviceRegVO> list = new ArrayList<DeviceRegVO>();

		DeviceRegVO deviceRegVO = new DeviceRegVO();

		// DCU
		deviceRegVO.setDeviceName("DCU");
		deviceRegVO.setDeviceRegConut((int) dcuInfoDAO.count());
		deviceRegVO.setType("electric");
		list.add(deviceRegVO);

		deviceRegVO = new DeviceRegVO();
		// Modem
		deviceRegVO.setDeviceName("Modem");
		deviceRegVO.setDeviceRegConut((int) modemInfoDAO.count());
		deviceRegVO.setType("electric");
		list.add(deviceRegVO);

		deviceRegVO = new DeviceRegVO();
		// Meter
		deviceRegVO.setDeviceName("Meter");
		deviceRegVO.setDeviceRegConut((int) meterInfoDAO.count());
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
