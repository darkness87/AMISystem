package com.cnu.ami.scheduler.batch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.scheduler.service.SchedulerService;
import com.cnu.ami.search.dao.entity.RegionEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 기상청 동네예보 통보문 조회서비스 오픈API활용
 * @author sookwon 날씨 데이터 가져오기
 */

@Slf4j
@Component
public class Weather {

	@Autowired
	SchedulerService schedulerService;

	@Scheduled(cron = "0 * * * * *") // 1시간 주기
	public void task() throws Exception {

		Date date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("YYYYMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH00");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String time = format2.format(cal.getTime());

		cal.add(Calendar.HOUR, -1); // 1시간전

		String ymd = format1.format(cal.getTime());
		String hm = format2.format(cal.getTime());

		List<RegionEntity> data = schedulerService.getRegionData();

		if (data == null) {
			throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.NULL_EXCEPTION, "좌표 정보가 없습니다.");
		}

		for (int i = 0; data.size() > i; i++) {

			String nx = String.valueOf(data.get(i).getNx());
			String ny = String.valueOf(data.get(i).getNy());

			String result = getWeatherData(ymd, hm, nx, ny);

			schedulerService.setResultData(data.get(i).getRSeq(), time, result);

		}

		log.info("weather scheduled End... (Count:{})", data.size());

	}
	
	public String getWeatherData(String ymd, String hm, String nx, String ny) throws Exception {
		log.info("weather scheduled task : 초단기예보 : {} / {} / {} / {}", ymd, hm, nx, ny);

		String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst";
		String serviceKey = "G%2FFGCoICYRoOQK3Jo14m46nxUnXLIhHLhkan2k9hRUuTBLBxc4XneGo3V0N5ao7CAtBOhOb7HpiLpKgHrRmfuQ%3D%3D";
		// TODO 추후 운영 계정으로 바꿔야 함 (현재 개인 계정)

		String numOfRows = "100";
		String pageNo = "1";
		String dataType = "JSON";

		StringBuilder urlBuilder = new StringBuilder(apiUrl);
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(dataType, "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(ymd, "UTF-8")); /*15년 12월 1일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(hm, "UTF-8")); /*06시 발표(정시단위)*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점 Y 좌표*/

		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		log.info("Response code: {}", conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		String result = sb.toString();

		return result;

	};

}
