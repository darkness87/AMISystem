package com.cnu.ami.scheduler.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "WEATHER")
public class WeatherEntity {

	@Id
	@Column(name = "WSEQ")
	private int WSEQ;

}
