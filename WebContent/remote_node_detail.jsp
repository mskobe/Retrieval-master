<%@page import="com.zj.retrieval.master.Util"%>
<%@page import="org.apache.http.impl.client.BasicResponseHandler"%>
<%@page import="org.apache.http.client.ResponseHandler"%>
<%@page import="org.apache.http.HttpEntity"%>
<%@page import="org.apache.http.impl.client.DefaultHttpClient"%>
<%@page import="org.apache.http.HttpResponse"%>
<%@page import="org.apache.http.client.entity.UrlEncodedFormEntity"%>
<%@page import="org.apache.http.protocol.HTTP"%>
<%@page import="org.apache.http.message.BasicNameValuePair"%>
<%@page import="org.apache.http.NameValuePair"%>
<%@page import="org.apache.http.client.methods.HttpPost"%>
<%@page import="org.apache.http.client.HttpClient"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.zj.retrieval.master.Attribute"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%
	String nodeId = request.getParameter("node_id");
	String url = request.getParameter("url");
	url = url.startsWith("http://") ? url : "http://" + url;
	String userName = request.getParameter("user_name");
	String userPwd = request.getParameter("user_pwd");
	
	DefaultHttpClient httpclient = new DefaultHttpClient();
	
	HttpPost httpost = new HttpPost(Util.urlConnect(url, "node/query_for_remote"));
    List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    nvps.add(new BasicNameValuePair("user_name", userName));
    nvps.add(new BasicNameValuePair("user_pwd", userPwd));
    nvps.add(new BasicNameValuePair("node_id", nodeId));
    httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
    ResponseHandler<String> responseHandler = new BasicResponseHandler();
    String responseBody = httpclient.execute(httpost, responseHandler);
	
	
	//打印返回的信息
	responseBody = responseBody.trim();
	System.out.println(responseBody);

	JSONObject jResp = new JSONObject(responseBody);
	boolean isError = jResp.getBoolean("isError");
	if (isError) {
		out.print(jResp.getString("message"));
		out.print("</html>");
		out.flush();
		return;
	}
	JSONObject j = jResp.getJSONObject("message");
	String name = j.getString("name");
	String enName = j.getString("name_en");
	String desc = j.getString("desc");
	String uri = j.getString("uri");
	String uriName = j.getString("uri_name");
	String owl = j.getString("owl");
	List<String> images = new ArrayList<String>();
	JSONArray jImages = j.getJSONArray("images");
	for (int i = 0; i < jImages.length(); i++) {
		images.add(Util.urlConnect(url, jImages.getString(i)));
	}
	JSONArray jUserFields = j.getJSONArray("user_field");
	Map<String, String> userField = new HashMap<String, String>();
	for (int i = 0; i < jUserFields.length(); i++) {
		JSONObject jField = jUserFields.getJSONObject(i);
		userField.put(jField.getString("key"), jField.getString("value"));
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="bootstrap-xtra/bootstrap.css" rel="stylesheet">
<style type="text/css">
      body {
        padding-top: 60px;
      }
</style>
<link href="common.css" type="text/css" rel="stylesheet" />
<title>ID: <%=nodeId %> FROM <%=url %></title>
</head>
<body>
    <div class="topbar">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="#">Fishery Retrieval</a>
          <ul class="nav">
            <li class="active"><a href="index.jsp">Home</a></li>
            <li><a href="retrieval_start.jsp">Common Search</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
          <p class="pull-right">Logged in as <a href="#">username</a></p>
        </div>
      </div>
    </div>
<table>
	<tr><td>ID</td><td><%=nodeId %></td></tr>
	<tr><td>NAME: </td><td><%=name %></td></tr>
	<tr><td>NAME_EN: </td><td><%=enName %></td></tr>
	<tr><td>DESC: </td><td><%=desc %></td></tr>
	<tr><td>URI: </td><td><%=uri %></td></tr>
	<tr><td>URI_NAME: </td><td><%=uriName %></td></tr>
	<tr><td>OWL: </td><td><%=owl %></td></tr>
	<tr><td colspan='2'>====== User Field ======</td></tr>
	<% for (Entry<String, String> entry : userField.entrySet()) { %>
			<tr><td>KEY: <%=entry.getKey() %></td><td>VALUE: <%=entry.getValue() %></td></tr>
	<% } %>
	<tr><td colspan='2'>====== Images ======</td></tr>
	<% for (String imageUrl : images) { %>
			<tr><td colspan='2'><img src='<%=imageUrl %>'/></td></tr>
	<% } %>
<%-- 	<% for (Attribute attrItem : attrs) { %> --%>
<%-- 			<tr><td colspan='2'>====== Attribute: <%=attrItem.getName() %> ======</td></tr> --%>
<!-- 			<tr><td>NAME: </td><td></td></tr> -->
<!-- 			<tr><td>NAME_EN: </td><td></td></tr> -->
<!-- 			<tr><td>DESC: </td><td></td></tr> -->
<%-- 			<% for (Entry<String, String> attrUserFiledEntry : attrItem.getUserFields().entrySet()) { %> --%>
<%-- 				<tr><td>KEY: <%=attrUserFiledEntry.getKey() %></td><td>VALUE: <%=attrUserFiledEntry.getValue() %></td></tr> --%>
<%-- 			<% } %> --%>
<%-- 			<tr><td colspan='2'><img src='<%=attrItem.getImage() %>'/></td></tr> --%>
<%-- 	<% } %> --%>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
	<tr><td></td><td></td></tr>
</table>
</body>
</html>