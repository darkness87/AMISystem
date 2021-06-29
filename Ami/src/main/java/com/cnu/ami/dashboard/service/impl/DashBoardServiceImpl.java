package com.cnu.ami.dashboard.service.impl;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import com.cnu.ami.dashboard.dao.document.HourRateTemp;
import com.cnu.ami.dashboard.dao.document.UseDayHourTemp;
import com.cnu.ami.dashboard.dao.entity.RegionNameIneterfaceVO;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceErrorCountVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllListVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateHourVO;
import com.cnu.ami.dashboard.models.RateListCountVO;
import com.cnu.ami.dashboard.models.RateRealVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ReadingDayInfoVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllListVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.UseLocationVO;
import com.cnu.ami.dashboard.models.WeatherDataVO;
import com.cnu.ami.dashboard.models.WeatherVO;
import com.cnu.ami.dashboard.service.DashBoardService;
import com.cnu.ami.device.equipment.dao.DcuInfoDAO;
import com.cnu.ami.device.equipment.dao.MeterInfoDAO;
import com.cnu.ami.device.equipment.dao.ModemInfoDAO;
import com.cnu.ami.device.estate.dao.EstateDAO;
import com.cnu.ami.device.estate.dao.entity.EstateEntity;
import com.cnu.ami.device.estate.dao.entity.EstateReadingFirstInterfaceVO;
import com.cnu.ami.device.server.dao.ServerDAO;
import com.cnu.ami.device.server.dao.entity.ServerRegionIneterfaceVO;
import com.cnu.ami.failure.reading.dao.FailureReadingDAO;
import com.cnu.ami.metering.info.models.EstateListReadingCountVO;
import com.cnu.ami.metering.mboard.dao.MBoardDAO;
import com.cnu.ami.metering.mboard.dao.document.EstateCountTemp;
import com.cnu.ami.metering.mboard.dao.entity.MBoardCountInterfaceVO;
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
	private MBoardDAO mBoardDAO;

	@Autowired
	private EstateDAO estateDAO;

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

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatDcu(today);

		String[] jsonRawString = { String.format("{ $match: { '$or':[{'day':'%s'},{'day':'%s'}] } }", today, yesterday),
				String.format("{ $unwind: { path: '$mids' } }"),
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

		List<UseDayHourTemp> data = result.getMappedResults();

		UseDayHourAllVO useDayHourAllVO = new UseDayHourAllVO();

		List<UseDayHourAllListVO> todaylist = new ArrayList<UseDayHourAllListVO>();
		List<UseDayHourAllListVO> yesterdaylist = new ArrayList<UseDayHourAllListVO>();
		UseDayHourAllListVO useDayHourAllListVO = new UseDayHourAllListVO();

		for (int i = 0; i < data.size(); i++) {

			if (i == 24 || i == 49) { // 중간 사이값 삭제 // 전일에서 금일 사이 중복 값
				continue;
			}

			useDayHourAllListVO = new UseDayHourAllListVO();

			if (data.get(i).getDay().equals(today)) {
				useDayHourAllListVO.setTime(data.get(i).getHour());
				if (data.get(i + 1).getSum() == 0) {
					useDayHourAllListVO.setUse(0);
				} else if (data.get(i + 1).getSum() < data.get(i).getSum()) {
					useDayHourAllListVO.setUse(0);
				} else {
					useDayHourAllListVO.setUse((data.get(i + 1).getSum() - data.get(i).getSum()));
				}

				useDayHourAllListVO.setLp(data.get(i).getSum());

				todaylist.add(useDayHourAllListVO);
			} else if (data.get(i).getDay().equals(yesterday)) {
				useDayHourAllListVO.setTime(data.get(i).getHour());
				if (data.get(i + 1).getSum() == 0) {
					useDayHourAllListVO.setUse(0);
				} else if (data.get(i + 1).getSum() < data.get(i).getSum()) {
					useDayHourAllListVO.setUse(0);
				} else {
					useDayHourAllListVO.setUse((data.get(i + 1).getSum() - data.get(i).getSum()));
				}

				useDayHourAllListVO.setLp(data.get(i).getSum());

				yesterdaylist.add(useDayHourAllListVO);
			}
		}

		long sumUse = 0;
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

		ServerRegionIneterfaceVO region = serverDAO.findFirstBySSEQ(1); // WAS/WEB 서버 SSEQ : 1

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String today = dateFormat.format(cal.getTime());

		WeatherEntity data = weatherDAO.findFirstByRSEQAndFCSTDATEOrderByFCSTTIMEDesc(region.getRSEQ(), today);
		RegionNameIneterfaceVO regionName = searchRegionDAO.findFirstByrSeq(region.getRSEQ());

		WeatherVO weatherVO = new WeatherVO();

		if (data == null) {

		} else {

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
		}

		return weatherVO;
	}

	@Override
	public WeatherDataVO getWeatherDataWeatherAll() throws Exception {

		ServerRegionIneterfaceVO region = serverDAO.findFirstBySSEQ(1); // WAS/WEB 서버 SSEQ : 1
		RegionNameIneterfaceVO regionName = searchRegionDAO.findFirstByrSeq(region.getRSEQ());

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -12); // 12시간전 미검침 장애정보 // TEST시 +1
		date = new Date(cal.getTimeInMillis());

		int houseCount = estateDAO.getEstateHouseCount();

		int failCount = failureReadingDAO.getAllCount(date.getTime() / 1000);

		WeatherDataVO weatherDataVO = new WeatherDataVO();

		float count = (failCount / houseCount) * 100;

		weatherDataVO.setLocation(regionName.getrName());
		if (count <= 5) {
			weatherDataVO.setCodeValue(0); // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
		} else if (count > 5 && count <= 15) {
			weatherDataVO.setCodeValue(1); // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
		} else if (count > 15) {
			weatherDataVO.setCodeValue(2); // 0:좋음, 1:보통, 2:나쁨 => 재확인후 결정
		}
		weatherDataVO.setDate(new Date());

		return weatherDataVO;

	}

	@Override
	public List<DashBoardMapVO> getLocationFailureMapInfo() throws Exception {

		List<DashBoardMapVO> dashmap = new ArrayList<DashBoardMapVO>();
		DashBoardMapVO dashBoardMapVO = new DashBoardMapVO();

		for (int i = 0; i < 17; i++) {
			dashBoardMapVO = new DashBoardMapVO();

			Random random = new Random();

			if (i == 0) {
				dashBoardMapVO.setHckey("kr-so"); // 서울특별시
				dashBoardMapVO.setValue(random.nextInt(30));
			} else if (i == 1) {
				dashBoardMapVO.setHckey("kr-pu"); // 부산광역시
				dashBoardMapVO.setValue(0);
			} else if (i == 2) {
				dashBoardMapVO.setHckey("kr-tg"); // 대구광역시
				dashBoardMapVO.setValue(0);
			} else if (i == 3) {
				dashBoardMapVO.setHckey("kr-in"); // 인천광역시
				dashBoardMapVO.setValue(random.nextInt(20));
			} else if (i == 4) {
				dashBoardMapVO.setHckey("kr-kj"); // 광주광역시
				dashBoardMapVO.setValue(random.nextInt(20));
			} else if (i == 5) {
				dashBoardMapVO.setHckey("kr-tj"); // 대전광역시
				dashBoardMapVO.setValue(random.nextInt(30));
			} else if (i == 6) {
				dashBoardMapVO.setHckey("kr-ul"); // 울산광역시
				dashBoardMapVO.setValue(0);
			} else if (i == 7) {
				dashBoardMapVO.setHckey("kr-kg"); // 경기도
				dashBoardMapVO.setValue(random.nextInt(25));
			} else if (i == 8) {
				dashBoardMapVO.setHckey("kr-kw"); // 강원도
				dashBoardMapVO.setValue(random.nextInt(15));
			} else if (i == 9) {
				dashBoardMapVO.setHckey("kr-gb"); // 충청북도
				dashBoardMapVO.setValue(random.nextInt(10));
			} else if (i == 10) {
				dashBoardMapVO.setHckey("kr-gn"); // 충청남도
				dashBoardMapVO.setValue(random.nextInt(20));
			} else if (i == 11) {
				dashBoardMapVO.setHckey("kr-cb"); // 전라북도
				dashBoardMapVO.setValue(0);
			} else if (i == 12) {
				dashBoardMapVO.setHckey("kr-2685"); // 전라남도
				dashBoardMapVO.setValue(random.nextInt(10));
			} else if (i == 13) {
				dashBoardMapVO.setHckey("kr-2688"); // 경상북도
				dashBoardMapVO.setValue(random.nextInt(50));
			} else if (i == 14) {
				dashBoardMapVO.setHckey("kr-kn"); // 경상남도
				dashBoardMapVO.setValue(0);
			} else if (i == 15) {
				dashBoardMapVO.setHckey("kr-cj"); // 제주특별자치도
				dashBoardMapVO.setValue(random.nextInt(25));
			} else if (i == 16) {
				dashBoardMapVO.setHckey("kr-sj"); // 세종특별자치시
				dashBoardMapVO.setValue(0);
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
	public List<UseLocationVO> getLocationUseList() throws Exception {

		List<UseLocationVO> list = new ArrayList<UseLocationVO>();

		UseLocationVO useLocationVO = new UseLocationVO();

		List<UseDayHourAllListVO> useData = new ArrayList<UseDayHourAllListVO>();

		UseDayHourAllListVO useDayHourAllListVO = new UseDayHourAllListVO();

		useData.add(useDayHourAllListVO);

		useLocationVO.setData(useData);

		list.add(useLocationVO);

		return list;
	}

	@Override
	public DeviceErrorCountVO getDeviceErrorCount() throws Exception {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -12);

		DeviceErrorCountVO deviceErrorCountVO = new DeviceErrorCountVO();

		int meterCount = meterInfoDAO.getMeterCount();
		int serverCount = serverDAO.getServerCount();
		int dcuCount = dcuInfoDAO.getDcuCount();
		int modemCount = modemInfoDAO.getModemCount();
		int lteCount = dcuInfoDAO.getLteCount();

		int meterErrorCount = meterInfoDAO.getMeterErrorCount(cal.getTimeInMillis() / 1000);
		int serverErrorCount = serverDAO.getServerErrorCount();
		int dcuErrorCount = dcuInfoDAO.getDcuErrorCount();
		int modemErrorCount = 0;
		int lteErrorCount = dcuInfoDAO.getLteErrorCount();

		deviceErrorCountVO.setMeterOperationCount(meterCount);
		deviceErrorCountVO.setMeterErrorCount(meterErrorCount);

		deviceErrorCountVO.setServerOperationCount(serverCount);
		deviceErrorCountVO.setServerErrorCount(serverErrorCount);

		deviceErrorCountVO.setDcuOperationCount(dcuCount);
		deviceErrorCountVO.setDcuErrorCount(dcuErrorCount);

		deviceErrorCountVO.setModemOperationCount(modemCount);
		deviceErrorCountVO.setModemErrorCount(modemErrorCount);

		deviceErrorCountVO.setLteOperationCount(lteCount);
		deviceErrorCountVO.setLteErrorCount(lteErrorCount);

		return deviceErrorCountVO;
	}

	@Override
	public List<DashBoardMapVO> getLocationRateMapInfo() throws Exception {

		Date date = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		SimpleDateFormat minFormat = new SimpleDateFormat("mm");

		String year = yearFormat.format(date);
		float hour = Integer.valueOf(hourFormat.format(date));
		int min = Integer.valueOf(minFormat.format(date));

		if (min >= 0 && min < 15) {
			hour = hour * 4;
		} else if (min >= 15 && min < 30) {
			hour = (hour * 4) + 1;
		} else if (min >= 30 && min < 45) {
			hour = (hour * 4) + 2;
		} else if (min >= 45 && min <= 59) {
			hour = (hour * 4) + 3;
		} else {
			hour = hour * 4;
		}

		List<EstateEntity> estate = estateDAO.getEstate();

		List<EstateListReadingCountVO> estateListCount = new ArrayList<EstateListReadingCountVO>();
		EstateListReadingCountVO estateListReadingCountVO = new EstateListReadingCountVO();

		for (EstateEntity est : estate) {
			estateListReadingCountVO = new EstateListReadingCountVO();

			CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
			String collectionName = collectionNameFormat.formatYear(est.getGSeq(), year);

			String[] jsonRawString = { String.format("{$match: { day: '%s' } }", dateFormat.format(date)),
					"{ $project: { did: '$did', day: '$day', idxStart: '$idxStart', idxEnd: '$idxEnd', idx: { $subtract: ['$idxEnd', '$idxStart'] } } }",
					"{ $group: { _id: { day: '$day' }, sum: { '$sum': '$idx' } } }",
					"{ $project: { day: '$_id.day', sum: '$sum' } }", "{ $sort: { did: 1 } }" };

			Aggregation aggregation = Aggregation.newAggregation(
					new CnuAggregationOperation(Document.parse(jsonRawString[0])),
					new CnuAggregationOperation(Document.parse(jsonRawString[1])),
					new CnuAggregationOperation(Document.parse(jsonRawString[2])),
					new CnuAggregationOperation(Document.parse(jsonRawString[3])),
					new CnuAggregationOperation(Document.parse(jsonRawString[4])));

			AggregationResults<EstateCountTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
					EstateCountTemp.class);

			EstateCountTemp data = result.getUniqueMappedResult();

			if (data == null) {
				continue;
			}

			estateListReadingCountVO.setEstateSeq(est.getGSeq());
			estateListReadingCountVO.setRegionSeq(est.getRSeq());
			estateListReadingCountVO.setReadingCount(data.getSum());

			estateListCount.add(estateListReadingCountVO);
		}

		List<DashBoardMapVO> dashmap = new ArrayList<DashBoardMapVO>();
		DashBoardMapVO dashReadingMapVO = new DashBoardMapVO();

		List<MBoardCountInterfaceVO> house = mBoardDAO.getHouseCount();

		for (int i = 0; i < house.size(); i++) {
			int count = 0;

			for (EstateListReadingCountVO readingCount : estateListCount) {
				if (readingCount.getRegionSeq() == house.get(i).getRSEQ()) {
					count = count + readingCount.getReadingCount();
				}
			}

			dashReadingMapVO = new DashBoardMapVO();

			float val = 0.0f;
			try {
				val = 100.0f - (((house.get(i).getCOUNT() * hour) - count) / (house.get(i).getCOUNT() * hour) * 100.0f);

				if (Float.isNaN(val) || Float.isInfinite(val)) {
					// val = 0.0f;
					val = Float.NaN;
				}

			} catch (Exception e) {
				val = 0.0f;
			}

			if (house.get(i).getRSEQ() == 2) {
				dashReadingMapVO.setHckey("kr-so"); // 서울특별시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 3) {
				dashReadingMapVO.setHckey("kr-pu"); // 부산광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 4) {
				dashReadingMapVO.setHckey("kr-tg"); // 대구광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 5) {
				dashReadingMapVO.setHckey("kr-in"); // 인천광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 6) {
				dashReadingMapVO.setHckey("kr-kj"); // 광주광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 7) {
				dashReadingMapVO.setHckey("kr-tj"); // 대전광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 8) {
				dashReadingMapVO.setHckey("kr-ul"); // 울산광역시
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 9) {
				int etcCount = 0;
				for (EstateListReadingCountVO readingCount : estateListCount) {
					if (readingCount.getRegionSeq() == 1) {
						etcCount = etcCount + readingCount.getReadingCount(); // 판교 더해주기
					}
				}
				try {
					val = 100.0f - ((((house.get(i).getCOUNT() + house.get(0).getCOUNT()) * hour) - (count + etcCount))
							/ ((house.get(i).getCOUNT() + house.get(0).getCOUNT()) * hour) * 100.0f);

					if (Float.isNaN(val) || Float.isInfinite(val)) {
						val = 0.0f;
					}

				} catch (Exception e) {
					val = 0.0f;
				}
				dashReadingMapVO.setHckey("kr-kg"); // 경기도
				dashReadingMapVO.setValue(val); // 판교 더해주기
			} else if (house.get(i).getRSEQ() == 10) {
				dashReadingMapVO.setHckey("kr-kw"); // 강원도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 11) {
				dashReadingMapVO.setHckey("kr-gb"); // 충청북도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 12) {
				dashReadingMapVO.setHckey("kr-gn"); // 충청남도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 13) {
				dashReadingMapVO.setHckey("kr-cb"); // 전라북도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 14) {
				dashReadingMapVO.setHckey("kr-2685"); // 전라남도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 15) {
				dashReadingMapVO.setHckey("kr-2688"); // 경상북도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 16) {
				dashReadingMapVO.setHckey("kr-kn"); // 경상남도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 17) {
				dashReadingMapVO.setHckey("kr-cj"); // 제주특별자치도
				dashReadingMapVO.setValue(val);
			} else if (house.get(i).getRSEQ() == 18) {
				dashReadingMapVO.setHckey("kr-sj"); // 세종특별자치시
				dashReadingMapVO.setValue(val);
			} else {
				continue;
			}

			dashmap.add(dashReadingMapVO);

		}

		return dashmap;
	}

	@Override
	public ReadingDayInfoVO getReadingDayInfo() throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월");
		SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
		
		String yearMonth = dateFormat.format(date);
		int day = Integer.valueOf(dayFormat.format(date));

		EstateReadingFirstInterfaceVO reading = estateDAO.getEstateReadingFirst(day);
		
		ReadingDayInfoVO readingDayInfoVO = new ReadingDayInfoVO();

		readingDayInfoVO.setReadingDay(yearMonth+" "+reading.getREADINGDAY()+"일");
		readingDayInfoVO.setHouseCount(reading.getHOUSECOUNT());
		
		SimpleDateFormat readingFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.valueOf(yearMonth.substring(0, 4)));
		cal.set(Calendar.MONTH, Integer.valueOf(yearMonth.substring(6, 8)) - 1);
		cal.set(Calendar.DATE, reading.getREADINGDAY());
		
		date = new Date(cal.getTimeInMillis());
		String toDate = readingFormat.format(date);
		
		int successCount = estateDAO.getEstateReadingSucess(reading.getREADINGDAY(),toDate);
		
		readingDayInfoVO.setHouseErrorCount(reading.getHOUSECOUNT()-successCount);
		readingDayInfoVO.setReadingRate((successCount/(reading.getHOUSECOUNT()*1.0f))*100.0f);

		return readingDayInfoVO;
	}

	@Override
	public RateRealVO getReadingRateDayHourAll() throws Exception {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String today = dateFormat.format(cal.getTime());
		
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		SimpleDateFormat minFormat = new SimpleDateFormat("mm");
		int hour = Integer.valueOf(hourFormat.format(cal.getTime()));
		int min = Integer.valueOf(minFormat.format(cal.getTime()));

		int minCount = 0;
		if (min >= 0 && min < 15) {
			minCount = 1;
		} else if (min >= 15 && min < 30) {
			minCount = 2;
		} else if (min >= 30 && min < 45) {
			minCount = 3;
		} else if (min >= 45 && min <= 59) {
			minCount = 4;
		}

		CollectionNameFormat collectionNameFormat = new CollectionNameFormat();
		String collectionName = collectionNameFormat.formatDcu(today);

		String[] jsonRawString = { String.format("{$match: { day: '%s' }}", today),
				"{$group: { _id: '$day', on: { $sum: '$cntOn' }, lp: { $sum: '$cntLp' }, total: { $sum: '$cntTotal' } }}" };

		Aggregation aggregation = Aggregation.newAggregation(
				new CnuAggregationOperation(Document.parse(jsonRawString[0])),
				new CnuAggregationOperation(Document.parse(jsonRawString[1])));

		AggregationResults<DayRateTemp> result = mongoTemplate.aggregate(aggregation, collectionName,
				DayRateTemp.class);

		DayRateTemp rate = result.getUniqueMappedResult();

		RateRealVO rateRealVO = new RateRealVO();

		if (rate.get_id().equals(today)) {
			rateRealVO.setRealMeterReadingRate((Float.valueOf(rate.getLp()) / (rate.getTotal()*1.0f)) * 100.0f);
			rateRealVO.setRealTimelyRate((Float.valueOf(rate.getOn()) / (rate.getTotal()*1.0f)) * 100.0f);
		}

		List<RateHourVO> hourRate = new ArrayList<RateHourVO>();

		RateHourVO rateHourVO = new RateHourVO();
		
		List<EstateEntity> estate = estateDAO.getEstate(); // 단지 목록
		
		int houseCount=0;
		int lp0=0;
		int lp1=0;
		int lp2=0;
		int lp3=0;
		int lp4=0;
		int lp5=0;
		int lp6=0;
		int lp7=0;
		int lp8=0;
		int lp9=0;
		int lp10=0;
		int lp11=0;
		int lp12=0;
		int lp13=0;
		int lp14=0;
		int lp15=0;
		int lp16=0;
		int lp17=0;
		int lp18=0;
		int lp19=0;
		int lp20=0;
		int lp21=0;
		int lp22=0;
		int lp23=0;
		int on0=0;
		int on1=0;
		int on2=0;
		int on3=0;
		int on4=0;
		int on5=0;
		int on6=0;
		int on7=0;
		int on8=0;
		int on9=0;
		int on10=0;
		int on11=0;
		int on12=0;
		int on13=0;
		int on14=0;
		int on15=0;
		int on16=0;
		int on17=0;
		int on18=0;
		int on19=0;
		int on20=0;
		int on21=0;
		int on22=0;
		int on23=0;
		
		for (EstateEntity data : estate) {
			
			String collectionEstateName = collectionNameFormat.formatDay(data.getGSeq(),today);
			
			String[] jsonRawStringEstateLp = { String.format("{$match: {day: '%s' }}", today),
			"{$unwind: {path: '$cntLp',includeArrayIndex: 'idx'}}",
			"{$group: {_id: {day: '$day',idx: '$idx'},sum: {$sum: '$cntLp'}}}",
			"{$project: {day: '$_id.day',idx: '$_id.idx',count: '$sum'}}",
			"{$sort: {idx: 1}}" };

			aggregation = Aggregation.newAggregation(
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateLp[0])),
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateLp[1])),
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateLp[2])),
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateLp[3])),
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateLp[4])));
			
			AggregationResults<HourRateTemp> estateLpResult = mongoTemplate.aggregate(aggregation, collectionEstateName, HourRateTemp.class);
			
			String[] jsonRawStringEstateOn = { String.format("{$match: {day: '%s' }}", today),
			"{$unwind: {path: '$cntOn',includeArrayIndex: 'idx'}}",
			"{$group: {_id: {day: '$day',idx: '$idx'},sum: {$sum: '$cntOn'}}}",
			"{$project: {day: '$_id.day',idx: '$_id.idx',count: '$sum'}}",
			"{$sort: {idx: 1}}" };
			
			aggregation = Aggregation.newAggregation(
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateOn[0])),
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateOn[1])),
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateOn[2])),
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateOn[3])),
					new CnuAggregationOperation(Document.parse(jsonRawStringEstateOn[4])));
			
			AggregationResults<HourRateTemp> estateOnResult = mongoTemplate.aggregate(aggregation, collectionEstateName, HourRateTemp.class);
			
			List<HourRateTemp> hourLpRate = estateLpResult.getMappedResults(); // 96개
			List<HourRateTemp> hourOnRate = estateOnResult.getMappedResults(); // 96개
			
			if(hourLpRate==null && hourOnRate==null) {
				continue;
			}
			
			if(hourLpRate.size()==0 && hourLpRate.size()==0) {
				continue;
			}
			
			List<RateListCountVO> lpRate = getHourRateCount(hourLpRate);
			List<RateListCountVO> onRate = getHourRateCount(hourOnRate);
			
			houseCount=houseCount+data.getCntHouse();
			lp0=lp0+lpRate.get(0).getCount();
			lp1=lp1+lpRate.get(1).getCount();
			lp2=lp2+lpRate.get(2).getCount();
			lp3=lp3+lpRate.get(3).getCount();
			lp4=lp4+lpRate.get(4).getCount();
			lp5=lp5+lpRate.get(5).getCount();
			lp6=lp6+lpRate.get(6).getCount();
			lp7=lp7+lpRate.get(7).getCount();
			lp8=lp8+lpRate.get(8).getCount();
			lp9=lp9+lpRate.get(9).getCount();
			lp10=lp10+lpRate.get(10).getCount();
			lp11=lp11+lpRate.get(11).getCount();
			lp12=lp12+lpRate.get(12).getCount();
			lp13=lp13+lpRate.get(13).getCount();
			lp14=lp14+lpRate.get(14).getCount();
			lp15=lp15+lpRate.get(15).getCount();
			lp16=lp16+lpRate.get(16).getCount();
			lp17=lp17+lpRate.get(17).getCount();
			lp18=lp18+lpRate.get(18).getCount();
			lp19=lp19+lpRate.get(19).getCount();
			lp20=lp20+lpRate.get(20).getCount();
			lp21=lp21+lpRate.get(21).getCount();
			lp22=lp22+lpRate.get(22).getCount();
			lp23=lp23+lpRate.get(23).getCount();
			on0=on0+onRate.get(0).getCount();
			on1=on1+onRate.get(1).getCount();
			on2=on2+onRate.get(2).getCount();
			on3=on3+onRate.get(3).getCount();
			on4=on4+onRate.get(4).getCount();
			on5=on5+onRate.get(5).getCount();
			on6=on6+onRate.get(6).getCount();
			on7=on7+onRate.get(7).getCount();
			on8=on8+onRate.get(8).getCount();
			on9=on9+onRate.get(9).getCount();
			on10=on10+onRate.get(10).getCount();
			on11=on11+onRate.get(11).getCount();
			on12=on12+onRate.get(12).getCount();
			on13=on13+onRate.get(13).getCount();
			on14=on14+onRate.get(14).getCount();
			on15=on15+onRate.get(15).getCount();
			on16=on16+onRate.get(16).getCount();
			on17=on17+onRate.get(17).getCount();
			on18=on18+onRate.get(18).getCount();
			on19=on19+onRate.get(19).getCount();
			on20=on20+onRate.get(20).getCount();
			on21=on21+onRate.get(21).getCount();
			on22=on22+onRate.get(22).getCount();
			on23=on23+onRate.get(23).getCount();
			
		}
				
		log.info("{}",houseCount);
		
		for (int i = 0; i < 24; i++) {
			rateHourVO = new RateHourVO();
			
			rateHourVO.setHour(i);
			
			float toCount = houseCount * 4.0f;
			
			if(i == hour) {
				toCount = houseCount * minCount * 1.0f;
			}

			if(i==0) {
				rateHourVO.setReadingRate((lp0/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on0/(toCount))*100.00f);
			} else if(i==1) {
				rateHourVO.setReadingRate((lp1/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on1/(toCount))*100.00f);
			} else if(i==2) {
				rateHourVO.setReadingRate((lp2/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on2/(toCount))*100.00f);
			} else if(i==3) {
				rateHourVO.setReadingRate((lp3/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on3/(toCount))*100.00f);
			} else if(i==4) {
				rateHourVO.setReadingRate((lp4/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on4/(toCount))*100.00f);
			} else if(i==5) {
				rateHourVO.setReadingRate((lp5/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on5/(toCount))*100.00f);
			} else if(i==6) {
				rateHourVO.setReadingRate((lp6/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on6/(toCount))*100.00f);
			} else if(i==7) {
				rateHourVO.setReadingRate((lp7/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on7/(toCount))*100.00f);
			} else if(i==8) {
				rateHourVO.setReadingRate((lp8/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on8/(toCount))*100.00f);
			} else if(i==9) {
				rateHourVO.setReadingRate((lp9/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on9/(toCount))*100.00f);
			} else if(i==10) {
				rateHourVO.setReadingRate((lp10/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on10/(toCount))*100.00f);
			} else if(i==11) {
				rateHourVO.setReadingRate((lp11/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on11/(toCount))*100.00f);
			} else if(i==12) {
				rateHourVO.setReadingRate((lp12/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on12/(toCount))*100.00f);
			} else if(i==13) {
				rateHourVO.setReadingRate((lp13/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on13/(toCount))*100.00f);
			} else if(i==14) {
				rateHourVO.setReadingRate((lp14/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on14/(toCount))*100.00f);
			} else if(i==15) {
				rateHourVO.setReadingRate((lp15/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on15/(toCount))*100.00f);
			} else if(i==16) {
				rateHourVO.setReadingRate((lp16/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on16/(toCount))*100.00f);
			} else if(i==17) {
				rateHourVO.setReadingRate((lp17/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on17/(toCount))*100.00f);
			} else if(i==18) {
				rateHourVO.setReadingRate((lp18/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on18/(toCount))*100.00f);
			} else if(i==19) {
				rateHourVO.setReadingRate((lp19/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on19/(toCount))*100.00f);
			} else if(i==20) {
				rateHourVO.setReadingRate((lp20/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on20/(toCount))*100.00f);
			} else if(i==21) {
				rateHourVO.setReadingRate((lp21/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on21/(toCount))*100.00f);
			} else if(i==22) {
				rateHourVO.setReadingRate((lp22/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on22/(toCount))*100.00f);
			} else if(i==23) {
				rateHourVO.setReadingRate((lp23/(toCount))*100.00f);
				rateHourVO.setTimelyRate((on23/(toCount))*100.00f);
			}
			
			hourRate.add(rateHourVO);
		}

		rateRealVO.setHourRate(hourRate);

		return rateRealVO;
	}
	
	public List<RateListCountVO> getHourRateCount(List<HourRateTemp> data) throws Exception{
		
		List<RateListCountVO> rateListCount = new ArrayList<RateListCountVO>();
		RateListCountVO rateListCountVO = new RateListCountVO();
		
		for (int i = 0; i < 24; i++) {
		
			rateListCountVO = new RateListCountVO();
			
			rateListCountVO.setHour(i);
			
			if(i==0) {
				rateListCountVO.setCount(data.get(0).getCount()+ data.get(1).getCount()+ data.get(2).getCount()+ data.get(3).getCount());
			} else if(i==1) {
				rateListCountVO.setCount(data.get(4).getCount()+ data.get(5).getCount()+ data.get(6).getCount()+ data.get(7).getCount());
			} else if(i==2) {
				rateListCountVO.setCount(data.get(8).getCount()+ data.get(9).getCount()+ data.get(10).getCount()+ data.get(11).getCount());
			} else if(i==3) {
				rateListCountVO.setCount(data.get(12).getCount()+ data.get(13).getCount()+ data.get(14).getCount()+ data.get(15).getCount());
			} else if(i==4) {
				rateListCountVO.setCount(data.get(16).getCount()+ data.get(17).getCount()+ data.get(18).getCount()+ data.get(19).getCount());
			} else if(i==5) {
				rateListCountVO.setCount(data.get(20).getCount()+ data.get(21).getCount()+ data.get(22).getCount()+ data.get(23).getCount());
			} else if(i==6) {
				rateListCountVO.setCount(data.get(24).getCount()+ data.get(25).getCount()+ data.get(26).getCount()+ data.get(27).getCount());
			} else if(i==7) {
				rateListCountVO.setCount(data.get(28).getCount()+ data.get(29).getCount()+ data.get(30).getCount()+ data.get(31).getCount());
			} else if(i==8) {
				rateListCountVO.setCount(data.get(32).getCount()+ data.get(33).getCount()+ data.get(34).getCount()+ data.get(35).getCount());
			} else if(i==9) {
				rateListCountVO.setCount(data.get(36).getCount()+ data.get(37).getCount()+ data.get(38).getCount()+ data.get(39).getCount());
			} else if(i==10) {
				rateListCountVO.setCount(data.get(40).getCount()+ data.get(41).getCount()+ data.get(42).getCount()+ data.get(43).getCount());
			} else if(i==11) {
				rateListCountVO.setCount(data.get(44).getCount()+ data.get(45).getCount()+ data.get(46).getCount()+ data.get(47).getCount());
			} else if(i==12) {
				rateListCountVO.setCount(data.get(48).getCount()+ data.get(49).getCount()+ data.get(50).getCount()+ data.get(51).getCount());
			} else if(i==13) {
				rateListCountVO.setCount(data.get(52).getCount()+ data.get(53).getCount()+ data.get(54).getCount()+ data.get(55).getCount());
			} else if(i==14) {
				rateListCountVO.setCount(data.get(56).getCount()+ data.get(57).getCount()+ data.get(58).getCount()+ data.get(59).getCount());
			} else if(i==15) {
				rateListCountVO.setCount(data.get(60).getCount()+ data.get(61).getCount()+ data.get(62).getCount()+ data.get(63).getCount());
			} else if(i==16) {
				rateListCountVO.setCount(data.get(64).getCount()+ data.get(65).getCount()+ data.get(66).getCount()+ data.get(67).getCount());
			} else if(i==17) {
				rateListCountVO.setCount(data.get(68).getCount()+ data.get(69).getCount()+ data.get(70).getCount()+ data.get(71).getCount());
			} else if(i==18) {
				rateListCountVO.setCount(data.get(72).getCount()+ data.get(73).getCount()+ data.get(74).getCount()+ data.get(75).getCount());
			} else if(i==19) {
				rateListCountVO.setCount(data.get(76).getCount()+ data.get(77).getCount()+ data.get(78).getCount()+ data.get(79).getCount());
			} else if(i==20) {
				rateListCountVO.setCount(data.get(80).getCount()+ data.get(81).getCount()+ data.get(82).getCount()+ data.get(83).getCount());
			} else if(i==21) {
				rateListCountVO.setCount(data.get(84).getCount()+ data.get(85).getCount()+ data.get(86).getCount()+ data.get(87).getCount());
			} else if(i==22) {
				rateListCountVO.setCount(data.get(88).getCount()+ data.get(89).getCount()+ data.get(90).getCount()+ data.get(91).getCount());
			} else if(i==23) {
				rateListCountVO.setCount(data.get(92).getCount()+ data.get(93).getCount()+ data.get(94).getCount()+ data.get(95).getCount());
			}
			
			rateListCount.add(rateListCountVO);
			
		}
		
		return rateListCount;
	};

}
