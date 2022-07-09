package com.fdmgroup.CurrencyConversion;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("wallet")
	private HashMap<String, Double> wallet;

	public User() {
		super();
	}

	public User(String name, HashMap<String, Double> wallet) {
		super();
		this.name = name;
		this.wallet = wallet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Double> getWallet() {
		return wallet;
	}

	public void setWallet(HashMap<String, Double> wallet) {
		this.wallet = wallet;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", wallet=" + wallet + "]";
	}
	
	

}
