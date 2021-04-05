package com.cnu.ami.scheduler.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.scheduler.dao.WeatherDAO;
import com.cnu.ami.scheduler.dao.entity.WeatherEntity;
import com.cnu.ami.scheduler.service.SchedulerService;
import com.cnu.ami.search.dao.SearchRegionDAO;
import com.cnu.ami.search.dao.entity.RegionEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	@Autowired
	WeatherDAO weatherDAO;

	@Autowired
	SearchRegionDAO searchRegionDAO;

	@Override
	public List<RegionEntity> getRegionData() throws Exception {

		List<RegionEntity> data = searchRegionDAO.findAll();

		return data;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setResultData(int rseq, String hourtime, String result) throws Exception {
//		System.out.println("==================================== : " + rseq);

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(result);

		JsonObject parse_response = (JsonObject) jsonObject.get("response");
		JsonObject parse_body = (JsonObject) parse_response.get("body");
		JsonObject parse_items = (JsonObject) parse_body.get("items");
		JsonArray parse_item = (JsonArray) parse_items.get("item");

		JsonObject weather = new JsonObject();
		Object baseDate;
		Object baseTime;
		Object fcstDate;
		Object fcstTime;
		Object category;
		Object value;

		WeatherEntity weatherEntity = new WeatherEntity();

		for (int i = 0; i < parse_item.size(); i++) {
			weather = (JsonObject) parse_item.get(i);
			baseDate = weather.get("baseDate");
			baseTime = weather.get("baseTime");
			fcstDate = weather.get("fcstDate");
			fcstTime = weather.get("fcstTime");
			category = weather.get("category");
			value = weather.get("fcstValue");

			if (hourtime.equals(fcstTime.toString().replaceAll("\"", ""))) {
//				System.out.println(rseq + " : " + fcstDate.toString().replaceAll("\"", "") + " : "
//						+ fcstTime.toString().replaceAll("\"", "") + " : " + category.toString().replaceAll("\"", "")
//						+ " : " + value.toString().replaceAll("\"", ""));

				weatherEntity.setRSEQ(rseq);
				weatherEntity.setBaseDate(baseDate.toString().replaceAll("\"", ""));
				weatherEntity.setBaseTime(baseTime.toString().replaceAll("\"", ""));
				weatherEntity.setFcstDate(fcstDate.toString().replaceAll("\"", ""));
				weatherEntity.setFcstTime(fcstTime.toString().replaceAll("\"", ""));

				if (category.toString().replaceAll("\"", "").equals("LGT")) {
					weatherEntity.setLGT(Integer.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("PTY")) {
					weatherEntity.setPTY(Integer.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("RN1")) {
					weatherEntity.setRN1(Float.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("SKY")) {
					weatherEntity.setSKY(Integer.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("T1H")) {
					weatherEntity.setT1H(Integer.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("REH")) {
					weatherEntity.setREH(Integer.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("UUU")) {
					weatherEntity.setUUU(Float.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("VVV")) {
					weatherEntity.setVVV(Float.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("VEC")) {
					weatherEntity.setVEC(Integer.valueOf(value.toString().replaceAll("\"", "")));
				} else if (category.toString().replaceAll("\"", "").equals("WSD")) {
					weatherEntity.setWSD(Integer.valueOf(value.toString().replaceAll("\"", "")));
				}

			}
		}

		weatherEntity.setWDATE(new Date().getTime() / 1000);

		weatherDAO.save(weatherEntity);

	}

}
