package com.ubiqube.etsi.mano.dao.mano;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MonitoringParams {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String name;
	private String value;
	private Date timestamp;
	private Long collectionPeriod;
	private String performanceMetric;

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getPerformanceMetric() {
		return performanceMetric;
	}

	public void setPerformanceMetric(final String performanceMetric) {
		this.performanceMetric = performanceMetric;
	}

	public Long getCollectionPeriod() {
		return collectionPeriod;
	}

	public void setCollectionPeriod(final Long collectionPeriod) {
		this.collectionPeriod = collectionPeriod;
	}

}
