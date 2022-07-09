package com.fdmgroup.CurrencyConversion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrencyConverter {
	
	public double convert(String fromCurrency, String toCurrency, double fromAmount) {
		
		double inverseRate = 0.0;
		double rate = 0.0;
		double toAmount;
		
		File JSONfile = new File("src/main/resources/rates.json");
		List<Currency> currencies = readFromJSONFile(JSONfile);
		
		for (Currency currency : currencies) {
			
			if (fromCurrency.toLowerCase().equals(currency.getCode().toLowerCase())) {
				inverseRate = currency.getInverseRate();
			} 
			
			if (toCurrency.toLowerCase().equals(currency.getCode().toLowerCase())) {
				rate = currency.getRate();
			} 
		}
		toAmount = fromAmount * inverseRate * rate;
		return toAmount;
	}
	
	private static List<Currency> readFromJSONFile(File file) {
		
		List<Currency> currencyArray = new ArrayList<Currency>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {			
			JsonNode node = mapper.readValue(file,  JsonNode.class);
			Iterator<JsonNode> childNodes = node.elements();
	        
			while (childNodes.hasNext()) {
	        	JsonNode subChildNode = childNodes.next();
	        	
	        	JsonNode codeNode = subChildNode.get("code");
	            String code = codeNode.asText();
	            
	            JsonNode rateNode = subChildNode.get("rate");
	            double rate = rateNode.asDouble();
	            
	            JsonNode inverseRateNode = subChildNode.get("inverseRate");
	            double inverseRate = inverseRateNode.asDouble();
	            
	            Currency currency = new Currency(code, rate, inverseRate);
	            currencyArray.add(currency);
	        }
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return currencyArray;
	}

}
