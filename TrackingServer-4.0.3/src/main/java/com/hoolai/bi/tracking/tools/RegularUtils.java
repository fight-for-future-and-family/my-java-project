package com.hoolai.bi.tracking.tools;


public class RegularUtils {

	public static boolean isNumber(String id) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(id);
		return match.matches();
	}
}
