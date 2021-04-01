package com.cnu.ami.metering.info.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.cnu.ami.metering.info.dao.DcuDAO;
import com.cnu.ami.metering.info.dao.MeterDAO;
import com.cnu.ami.metering.info.dao.RealTimeDAO;
import com.cnu.ami.metering.info.dao.entity.DcuInterfaceVO;
import com.cnu.ami.metering.info.dao.entity.MeterInterfaceVO;
import com.cnu.ami.metering.info.dao.entity.RealTimeInterfaceVO;
import com.cnu.ami.metering.info.models.CollectDcuVO;
import com.cnu.ami.metering.info.models.CollectMeterVO;
import com.cnu.ami.metering.info.models.RealTimeVO;
import com.cnu.ami.metering.info.service.InfoService;
import com.cnu.ami.metering.lookup.dao.document.LpDataTemp;

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	RealTimeDAO realTimeDAO;

	@Autowired
	DcuDAO dcuDAO;

	@Autowired
	MeterDAO meterDAO;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<RealTimeVO> getRealTimeData(int gseq) throws Exception {

		List<RealTimeInterfaceVO> data = realTimeDAO.getRealTimeData(gseq);

		List<RealTimeVO> list = new ArrayList<RealTimeVO>();
		RealTimeVO realTimeVO = new RealTimeVO();

		for (int i = 0; data.size() > i; i++) {
			realTimeVO = new RealTimeVO();

			realTimeVO.setRegionSeq(data.get(i).getRSEQ());
			realTimeVO.setEstateSeq(data.get(i).getGSEQ());
			realTimeVO.setBuildingSeq(data.get(i).getBSEQ());
			realTimeVO.setRegionName(data.get(i).getRNAME());
			realTimeVO.setEstateId(data.get(i).getGID());
			realTimeVO.setEstateName(data.get(i).getGNAME());
			realTimeVO.setBuildingName(data.get(i).getBNAME());
			realTimeVO.setHouseName(data.get(i).getHO());
			realTimeVO.setDcuId(data.get(i).getDID());
			realTimeVO.setMeterId(data.get(i).getMETER_ID());
			realTimeVO.setMac(data.get(i).getMAC());
			realTimeVO.setMeterTime(new Date(data.get(i).getMTIME() * 1000));
			realTimeVO.setFap(data.get(i).getFAP());
			realTimeVO.setRfap(data.get(i).getRFAP());
			realTimeVO.setUpdateDate(new Date(data.get(i).getUDATE() * 1000));

			list.add(realTimeVO);
		}

		return list;
	}

	@Override
	public List<CollectDcuVO> getDcuData(int gseq) throws Exception {

		List<DcuInterfaceVO> data = new ArrayList<DcuInterfaceVO>();

		if (gseq == 0) {
			data = dcuDAO.getDcuData();
		} else {
			data = dcuDAO.getDcuData(gseq);
		}

		List<CollectDcuVO> list = new ArrayList<CollectDcuVO>();
		CollectDcuVO collectDcuVO = new CollectDcuVO();

		for (int i = 0; data.size() > i; i++) {
			collectDcuVO = new CollectDcuVO();
			
			collectDcuVO.setRegionSeq(data.get(i).getRSEQ());
			collectDcuVO.setEstateSeq(data.get(i).getGSEQ());
			collectDcuVO.setBuildingSeq(data.get(i).getBSEQ());
			collectDcuVO.setRegionName(data.get(i).getRNAME());
			collectDcuVO.setEstateId(data.get(i).getGID());
			collectDcuVO.setEstateName(data.get(i).getGNAME());
			collectDcuVO.setBuildingName(data.get(i).getBNAME());
			collectDcuVO.setDcuId(data.get(i).getDID());

			list.add(collectDcuVO);
		}

		return list;
	}

	@Override
	public List<CollectMeterVO> getMeterData(String day, String dcuId) throws Exception {

		List<MeterInterfaceVO> meterdata = meterDAO.getMeterData(dcuId);

		Query query = new Query().addCriteria(Criteria.where("day").is(day))
				.addCriteria(Criteria.where("did").is(dcuId));
		List<LpDataTemp> lpdata = mongoTemplate.find(query, LpDataTemp.class, "CASS_1_2021_RAW_LP");

		//////////////// TODO
//		Criteria criteria = new Criteria();
//		criteria.andOperator(Criteria.where("day").is(day),Criteria.where("did").is(dcuId));
//		
//		MatchOperation matchOperation = Aggregation.match(criteria);
//		
//		UnwindOperation unwindOperation = Aggregation.unwind("$cntOn");
//		
//		GroupOperation groupOperation = Aggregation.group("_id", "did", "mid", "day", "is", "ie").sum("$cntOn").as("$sum");
//		
//		ProjectionOperation projectionOperation = Aggregation.project("_id", "sss","cnt");
//		
//		
//		collection.aggregate(Arrays.asList(match(and(regex("day", "20210330"), eq("did", "CNU_TEST_1"))),
//				unwind("$cntOn"), 
//				group(and(eq("did", "$did"), eq("mid", "$mid"), eq("day", "$day"), eq("is", "$idxStart"), eq("ie", "$idxEnd")), sum("sum", "$cntOn")), 
//				project(computed("_id", "$_id"), computed("sss", eq("$subtract", Arrays.asList("$sum", 0L))), computed("cnt", eq("$add", Arrays.asList(1L, eq("$subtract", Arrays.asList("$_id.ie", "$_id.is")))))), 
//				group(and(eq("did", "$_id.did"), eq("mid", "$_id.mid"), eq("day", "$_id.day")), sum("lpcnt", "$cnt"), sum("result", "$sss")),
//				project(computed("_id", "$_id"), computed("rate", eq("$divide", Arrays.asList("$result", "$lpcnt"))))));
//		
		////////////////

		List<CollectMeterVO> list = new ArrayList<CollectMeterVO>();
		CollectMeterVO collectMeterVO = new CollectMeterVO();

		for (int i = 0; meterdata.size() > i; i++) {

			for (int k = 0; lpdata.size() > k; k++) {
				if (meterdata.get(i).getMETER_ID().equals(lpdata.get(k).getMid())) {
					collectMeterVO = new CollectMeterVO();
					// 같으면 파일 진행

					collectMeterVO.setDcuId(meterdata.get(i).getDID());
					collectMeterVO.setMeterId(meterdata.get(i).getMETER_ID());
					collectMeterVO.setDeviceName(meterdata.get(i).getDEVICE_NAME());
					collectMeterVO.setMac(meterdata.get(i).getMAC());
					collectMeterVO.setReadingDay(meterdata.get(i).getMRD());
					collectMeterVO.setLpPeriod(meterdata.get(i).getLP_PERIOD());
					collectMeterVO.setMeterType(meterdata.get(i).getMTYPE());
					collectMeterVO.setHouseName(meterdata.get(i).getHO());

					int countLp = 0;
					for (int c = 0; lpdata.get(k).getCntLp().size() > c; c++) {
						countLp = lpdata.get(k).getCntLp().get(c) + countLp;
					}

					int countOn = 0;
					for (int c = 0; lpdata.get(k).getCntOn().size() > c; c++) {
						countOn = lpdata.get(k).getCntOn().get(c) + countOn;
					}

					collectMeterVO.setCountLp(countLp);
					collectMeterVO.setCountOn(countOn);

					int countTotal = (60 / meterdata.get(i).getLP_PERIOD()) * 24;
//					if(meterdata.get(i).getLP_PERIOD()==15) {
//						countTotal = 96;
//					} else if(meterdata.get(i).getLP_PERIOD()==60) {
//						countTotal = 24;
//					}

					collectMeterVO.setTotalLp(countTotal);

					collectMeterVO.setRateLp((countLp / Float.valueOf(countTotal)) * 100);
					collectMeterVO.setRateOn((countOn / Float.valueOf(countTotal)) * 100);

					list.add(collectMeterVO);

				} else {
					continue;
				}
			}

		}

		return list;
	}

}
