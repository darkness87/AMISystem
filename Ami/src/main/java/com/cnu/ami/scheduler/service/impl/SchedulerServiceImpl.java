package com.cnu.ami.scheduler.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnu.ami.scheduler.dao.WeatherDAO;
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
		System.out.println("============================================================================== : "+rseq);
		
		// TODO json 파서 관련
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(result);

		JsonObject parse_response = (JsonObject) jsonObject.get("response");
		JsonObject parse_body = (JsonObject) parse_response.get("body");
		JsonObject parse_items = (JsonObject) parse_body.get("items");
		JsonArray parse_item = (JsonArray) parse_items.get("item");
		
		String category = "";
		JsonObject weather = null;
		String day = "";
		String time = "";

		for (int i = 0; i < parse_item.size(); i++) {
			weather = (JsonObject) parse_item.get(i);
			
			System.out.println(hourtime);
			System.out.println(weather.get("fcstTime"));
			System.out.println(String.valueOf(weather.get("fcstTime")));
			
			if(hourtime.equals(String.valueOf(weather.get("fcstTime")))) {
			
			Object fcstValue = weather.get("fcstValue");
			Object fcstDate = weather.get("fcstDate");
			Object fcstTime = weather.get("fcstTime");
			category = String.valueOf(weather.get("category"));
			
			if (!day.equals(fcstDate.toString())) {
				day = fcstDate.toString();
			}
			if (!time.equals(fcstTime.toString())) {
				time = fcstTime.toString();
			}
			
			System.out.print("\tcategory : " + category);
			System.out.print(", fcst_Value : " + fcstValue);
			System.out.print(", fcstDate : " + fcstDate);
			System.out.println(", fcstTime : " + fcstTime);
			
			}else {
				continue;
			}
			
		}

	}

}
