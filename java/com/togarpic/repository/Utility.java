package com.togarpic.repository;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL =request.getRequestURI().toString();
		return siteURL;
	}
}
