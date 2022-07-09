package com.fdmgroup.CurrencyConversion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Currency {
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("rate")
	private double rate;
	
	@JsonProperty("inverseRate")
	private double inverseRate;
	
	@JsonIgnoreProperties({"alphaCode", "numericCode", "name", "date"})

	public Currency() {
		super();
	}

	public Currency(String code, double rate, double inverseRate) {
		super();
		this.code = code;
		this.rate = rate;
		this.inverseRate = inverseRate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getInverseRate() {
		return inverseRate;
	}

	public void setInverseRate(double inverseRate) {
		this.inverseRate = inverseRate;
	}

	@Override
	public String toString() {
		return "Currency [code=" + code + ", rate=" + rate + ", inverseRate=" + inverseRate + "]";
	}
	
	

}
