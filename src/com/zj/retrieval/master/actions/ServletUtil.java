package com.zj.retrieval.master.actions;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {
	public static String getPrettyRequestParameters(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		String lineSep = System.getProperty("line.separator");
		Enumeration<String> params = req.getParameterNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			sb.append(param).append("=").append(req.getParameter(param)).append(lineSep);
		}
		return sb.toString();
	}
}
