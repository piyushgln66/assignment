package com.sp.group.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.h2.util.StringUtils;
import com.sp.group.exception.CustomException;

public class EmailValidatorUtil {
	
	private final static String PATTERN_STRING = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	
	public static void validate(String email) throws CustomException  {
		if(StringUtils.isNullOrEmpty(email)) {
			throw new CustomException("Email address cannot be empty or null");
		}
		if(!email.matches(PATTERN_STRING)) {
			throw new CustomException("Please provide a valid email address");
		}
	}
	
	public static void validate(List<String> emails) throws CustomException  {
		for(String email : emails) {
			validate(email);
		}
	}
	
	public static List<String> removeDuplicate(List<String> emails) throws CustomException  {
		Set<String> set = new HashSet<String>();
		for(String email : emails) {
			set.add(email);
		}
		return new ArrayList<String>(set);
	}

}
