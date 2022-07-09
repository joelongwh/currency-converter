package com.fdmgroup.CurrencyConversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionProcessor {
	
	public static void main(String args[]) {
				
		try {
			
			// Create transaction file reader
			FileReader fileReader = new FileReader("src/main/resources/transactions.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			// Pass each transaction to be executed
			String line = new String();
			while ((line = bufferedReader.readLine()) != null) {
				executeTransaction(line);
			}
			
			// Close file reader and buffered reader
			fileReader.close();
			bufferedReader.close();
		
		// Catch any exceptions
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	
	
	public static void executeTransaction(String transaction) {
		
		// Split transaction line into individual arguments
		String[] strings = transaction.split(" ");
		String name = strings[0];
		String fromCurrency = strings[1];
		String toCurrency = strings[2];
		double fromAmount = Double.parseDouble(strings[3]);
		
		// Create CurrencyConverter object
		CurrencyConverter currencyConverter = new CurrencyConverter();
		
		// Get ArrayList of users
		List<User> userArrayList = getUsers();

		for (User user : userArrayList) {
			
			String userName = user.getName();
			boolean containsFromCurrency = user.getWallet().containsKey(fromCurrency);
			boolean containsToCurrency = user.getWallet().containsKey(toCurrency);
			
			// Check which user the transaction belongs to and if the user's wallet contains the FROM currency
			if (userName.equals(name) && containsFromCurrency) {
				
				double fromCurrencyBalance = user.getWallet().get(fromCurrency);
				
				// Check that user has enough money for transaction
				if (fromCurrencyBalance >= fromAmount) {
					
					double toAmount = currencyConverter.convert(fromCurrency, toCurrency, fromAmount);
					
					// Skip transactions where currency rate is unavailable
					if (toAmount == 0.0) {
							continue;
					}
					
					// Calculate balance for FROM currency and update user's wallet
					fromCurrencyBalance -= fromAmount;
					user.getWallet().replace(fromCurrency, fromCurrencyBalance);
						
					// Check if user's wallet contains the TO currency
					if (containsToCurrency) {
						
						// If yes, calculate balance for TO currency and update user's wallet
						double toCurrencyBalance = user.getWallet().get(toCurrency);
						toCurrencyBalance += toAmount;
						user.getWallet().replace(toCurrency, toCurrencyBalance);
						
					// If no, add TO currency to user's wallet
					} else {
						user.getWallet().put(toCurrency, toAmount);
					}
						
					// Update the users.json file
					updateUsersFile(userArrayList);
					
				}
																			
			} 
			
		}
				
	}
	
	
	public static void updateUsersFile(List<User> userArrayList) {
		
	    ObjectMapper mapper = new ObjectMapper();
	    
	    try {
			mapper.writeValue(Paths.get("src/main/resources/users.json").toFile(), userArrayList);
		
	    } catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	
	public static List<User> getUsers() {
		
		File JSONfile = new File("src/main/resources/users.json");
		User[] users = readFromJSONFile(JSONfile);
		List<User> userArrayList = Arrays.asList(users);
		return userArrayList;
	}
	
	
	private static User[] readFromJSONFile(File file) {
		
		User[] userArray = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			userArray = mapper.readValue(file, User[].class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return userArray;
	}

}
