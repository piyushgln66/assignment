package com.sp.group.util;

import org.h2.util.StringUtils;

import com.sp.group.exception.CustomException;

public class UpdateValidatorUtil {
	
	public static void validate(String text) throws CustomException  {
		if(StringUtils.isNullOrEmpty(text.trim())) {
			throw new CustomException("Update cannot be empty or null");
		}
	}
}
