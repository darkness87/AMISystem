package com.cnu.ami.dashboard.service.impl;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.cnu.ami.common.CnuAggregationOperation;
import com.cnu.ami.common.CollectionNameFormat;
import com.cnu.ami.dashboard.dao.document.DayLpFailureTemp;
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
import com.cnu.ami.dashboard.models.WeatherDataVO;
import com.cnu.ami.dashboard.models.WeatherVO;
import com.cnu.ami.dashboard.service.DashBoardService;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.MeterInfoDAO;
import com.cnu.ami.device.equipment.dao.ModemInfoDAO;
import com.cnu.ami.device.server.dao.ServerDAO;
import com.cnu.ami.device.server.dao.entity.ServerRegionIneterfaceVO;
import com.cnu.ami.failure.reading.dao.FailureReadingDAO;
import com.cnu.ami.failure.reading.dao.entity.LpSnapCountInterfaceVO;
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
	private FailureReadingDAO failureReadingDAO;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public UseDayHourAllVO getElectricUseDayHourAll() throws Exception {

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

		return useDayHourAllVO;
	}

	@Override
	public RateVO getElectricMeterReadingRateDayAll() throws Exception {

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

		return rateVO;
	}

	@Override
	public FailureAllVO getElectricFailureDayHourAll() throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatFault();

		String[] jsonRawString = { String.format("{$match: { day: '%s' }}", today),
				"{$group: { _id: { time: { $substr: [ \"$mstr\", 11, 2 ] } }, f1: { $sum : '$f1' }, f2: { $sum : '$f2' }, f3: { $sum : '$f3' }, f4: { $sum : '$f4' }, f5: { $sum : '$f5' } , f6: { $sum : '$f6' }, f7: { $sum : '$f7' }, f8: { $sum : '$f8' }, f9: { $sum : '$f9' }, f10: { $sum : '$f10' } , f11: { $sum : '$f11' }, f12: { $sum : '$f12' }, f13: { $sum : '$f13' }, f14: { $sum : '$f14' }, f15: { $sum : '$f15' } , f16: { $sum : '$f16' }, f17: { $sum : '$f17' }, f18: { $sum : '$f18' }, f19: { $sum : '$f19' }, f20: { $sum : '$f20' } , f21: { $sum : '$f21' }, f22: { $sum : '$f22' }, f23: { $sum : '$f23' }, f24: { $sum : '$f24' } }}",
				"{$project: { time: {'$convert': { 'input': '$_id.time', 'to': 'int' } }, total : { '$add' : [ '$f1', '$f2', '$f3', '$f4', '$f5', '$f6', '$f7', '$f8', '$f9' , '$f10', '$f11', '$f12', '$f13', '$f14', '$f15', '$f16', '$f17', '$f18', '$f19', '$f20', '$f21', '$f22', '$f23', '$f24'] } }}",
				"{$sort: { time: 1 }}" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])),
				new CnuAggregationOperation(Document.parse(jsonRawString[2])),
				new CnuAggregationOperation(Document.parse(jsonRawString[3])));

		AggregationResults<DayLpFailureTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				DayLpFailureTemp.class);

		List<DayLpFailureTemp> data = result.getMappedResults();

		FailureAllVO failureAllVO = new FailureAllVO();

		List<FailureAllListVO> list = new ArrayList<FailureAllListVO>();
		FailureAllListVO failureAllListVO = new FailureAllListVO();

		int count = 0;
		for (DayLpFailureTemp temp : data) {
			failureAllListVO = new FailureAllListVO();
			failureAllListVO.setTime(temp.getTime());
			failureAllListVO.setCount(temp.getTotal());

			count = count + temp.getTotal();

			list.add(failureAllListVO);
		}

		failureAllVO.setFailureTodayCount(count);
		failureAllVO.setDate(new Date());
		failureAllVO.setType("electric");

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
		weatherVO.setCodeSky(data.getSKY()); // 1:맑음, 3:약간흐림, 4:흐림
		weatherVO.setCodeRain(data.getPTY()); // 0:없음, 1:비, 2:비/눈(진눈개비), 3:눈, 4:소나기, 5:빗방울, 6:빗방울/눈날림, 7:눈날림

		cal.set(Integer.valueOf(data.getFCSTDATE().substring(0, 4)),
				Integer.valueOf(data.getFCSTDATE().substring(4, 6)) - 1,
				Integer.valueOf(data.getFCSTDATE().substring(6, 8)),
				Integer.valueOf(data.getFCSTTIME().substring(0, 2)),
				Integer.valueOf(data.getFCSTTIME().substring(2, 4)), 0);

		weatherVO.setDate(cal.getTime());

		return weatherVO;
	}

	@Override
	public WeatherDataVO getWeatherDataWeatherAll() throws Exception {

		ServerRegionIneterfaceVO region = serverDAO.findBySSEQ(1); // WAS/WEB 서버 SSEQ : 1
		RegionNameIneterfaceVO regionName = searchRegionDAO.findByrSeq(region.getRSEQ());

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -12); // 12시간전 미검침 장애정보 // TEST시 +1
		date = new Date(cal.getTimeInMillis());

		LpSnapCountInterfaceVO count = failureReadingDAO.getAllCount(date.getTime() / 1000);

		WeatherDataVO weatherDataVO = new WeatherDataVO();

		weatherDataVO.setLocation(regionName.getrName());
		if (count.getCount() <= 10) {
			weatherDataVO.setCodeValue(0); // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
		} else if (count.getCount() > 10 && count.getCount() <= 50) {
			weatherDataVO.setCodeValue(1); // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
		} else if (count.getCount() > 50) {
			weatherDataVO.setCodeValue(2); // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
		}
		weatherDataVO.setDate(new Date());

		return weatherDataVO;

	}

	@Override
	public List<DashBoardMapVO> getLocationFailureMapInfo() throws Exception {

		// TODO key,value 형식 해결 , 장애 데이터 넘기기

		List<DashBoardMapVO> dashmap = new ArrayList<DashBoardMapVO>();
		DashBoardMapVO dashBoardMapVO = new DashBoardMapVO();

		for (int i = 0; i < 17; i++) {
			dashBoardMapVO = new DashBoardMapVO();
			if (i == 0) {
				dashBoardMapVO.setHckey("kr-so"); // 서울특별시
				dashBoardMapVO.setValue(5);
			} else if (i == 1) {
				dashBoardMapVO.setHckey("kr-pu"); // 부산광역시
				dashBoardMapVO.setValue(7);
			} else if (i == 2) {
				dashBoardMapVO.setHckey("kr-tg"); // 대구광역시
				dashBoardMapVO.setValue(10);
			} else if (i == 3) {
				dashBoardMapVO.setHckey("kr-in"); // 인천광역시
				dashBoardMapVO.setValue(3);
			} else if (i == 4) {
				dashBoardMapVO.setHckey("kr-kj"); // 광주광역시
				dashBoardMapVO.setValue(3);
			} else if (i == 5) {
				dashBoardMapVO.setHckey("kr-tj"); // 대전광역시
				dashBoardMapVO.setValue(3);
			} else if (i == 6) {
				dashBoardMapVO.setHckey("kr-ul"); // 울산광역시
				dashBoardMapVO.setValue(3);
			} else if (i == 7) {
				dashBoardMapVO.setHckey("kr-kg"); // 경기도
				dashBoardMapVO.setValue(3);
			} else if (i == 8) {
				dashBoardMapVO.setHckey("kr-kw"); // 강원도
				dashBoardMapVO.setValue(3);
			} else if (i == 9) {
				dashBoardMapVO.setHckey("kr-gb"); // 충청북도
				dashBoardMapVO.setValue(3);
			} else if (i == 10) {
				dashBoardMapVO.setHckey("kr-gn"); // 충청남도
				dashBoardMapVO.setValue(3);
			} else if (i == 11) {
				dashBoardMapVO.setHckey("kr-cb"); // 전라북도
				dashBoardMapVO.setValue(3);
			} else if (i == 12) {
				dashBoardMapVO.setHckey("kr-2685"); // 전라남도
				dashBoardMapVO.setValue(3);
			} else if (i == 13) {
				dashBoardMapVO.setHckey("kr-2688"); // 경상북도
				dashBoardMapVO.setValue(3);
			} else if (i == 14) {
				dashBoardMapVO.setHckey("kr-kn"); // 경상남도
				dashBoardMapVO.setValue(3);
			} else if (i == 15) {
				dashBoardMapVO.setHckey("kr-cj"); // 제주특별자치도
				dashBoardMapVO.setValue(3);
			} else if (i == 16) {
				dashBoardMapVO.setHckey("kr-sj"); // 세종특별자치시
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
	public List<Object> getLocationUseList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
