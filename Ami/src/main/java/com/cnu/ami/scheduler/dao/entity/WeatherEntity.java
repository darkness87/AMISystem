package com.cnu.ami.scheduler.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(WeatherPk.class)
@Table(name = "WEATHER")
public class WeatherEntity {

	@Id
	@Column(name = "RSEQ") // 지역 SEQ번호
	private long RSEQ;

	@Id
	@Column(name = "FCST_DATE") // 예측일자
	private String FCSTDATE;

	@Id
	@Column(name = "FCST_TIME") // 예측시간
	private String FCSTTIME;

	@Column(name = "LGT") // 낙뢰
	private int LGT; // 0: 없음, 1: 있음

	@Column(name = "PTY") // 강수형태
	private int PTY; // 0: 없음, 1: 비, 2: 비/눈(진눈개비), 3: 눈, 4: 소나기, 5: 빗방울, 6: 빗방울/눈날림, 7: 눈날림

	@Column(name = "RN1") // 1시간 강수량 (mm)
	private float RN1;

	@Column(name = "SKY") // 하늘상태
	private int SKY; // 1: 맑음, 3: 구름많음, 4: 흐림

	@Column(name = "T1H") // 기온
	private int T1H;

	@Column(name = "REH") // 습도 (%)
	private int REH;

	@Column(name = "UUU") // 풍속정보 : 동서바람성분 (m/s)
	private float UUU;

	@Column(name = "VVV") // 풍속정보 : 남북바람성분 (m/s)
	private float VVV;

	@Column(name = "VEC") // 풍향 (deg)
	private int VEC;

	@Column(name = "WSD") // 풍속 (m/s)
	private int WSD;

	@Column(name = "WDATE") // 기록일시
	private long WDATE;

	@Column(name = "BASE_DATE") // 발표일자
	private String BASEDATE;

	@Column(name = "BASE_TIME") // 발표시간
	private String BASETIME;

}
