package com.zj.retrieval.master;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zj.retrieval.master.dao.NodeDao;
import com.zj.retrieval.master.dao.UserDao;

public class Util {
	public static ApplicationContext applicationContext = null;
	public static String IMAGE_PATH_PREFIX = "images/";
	
	public static ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			ServletContext servletCtx = ServletActionContext.getServletContext();
			WebApplicationContext springCtx = WebApplicationContextUtils.getWebApplicationContext(servletCtx);
			return springCtx;
		} else {
			return applicationContext;
		}
	}
	
	public static String getImageNameExcludePath(String url) {
		return url.substring(IMAGE_PATH_PREFIX.length(), url.length());
	}
	
	public static UserDao getUserDao() {
		ApplicationContext ctx = getApplicationContext();
		return (UserDao) ctx.getBean("userDao");
	}
	
	public static NodeDao getNodeDao() {
		ApplicationContext ctx = getApplicationContext();
		return (NodeDao) ctx.getBean("nodeDao");
	}
	
	public static String html(String content) {
		if(content==null) return "";        
	    String html = content;
	    html = html.replaceAll("'", "&apos;");
	    html = html.replaceAll("\"", "&quot;");
	    html = html.replaceAll("\t", "&nbsp;&nbsp;");
	    html = html.replaceAll(" ", "&nbsp;");
	    html = html.replaceAll("<", "&lt;");
	    html = html.replaceAll(">", "&gt;");
	    html = html.replaceAll("\n", "<br/>");
//	    html = html.replaceAll("&", "&amp;");
	    return html;
	}
	
	public static String urlConnect(String url, String connect) {
		return endsWithSlash(url) ? url + connect : url + "/" + connect;
	}
	
	private static boolean endsWithSlash(String str) {
		return str.endsWith("/") || str.endsWith("\\");
	}
}
