package com.business.validation;

public class ValidationUtility {
	
	public static Boolean checkForNullOrEmptyString(String testString) {
		if(testString !=null) {
			testString = testString.trim();
			if(testString.equals("")) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
}

