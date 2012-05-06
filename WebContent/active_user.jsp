<%@page import="com.zj.retrieval.master.User"%>
<%@page import="java.util.List"%>
<%@page import="com.zj.retrieval.master.dao.UserDao"%>
<%@page import="com.zj.retrieval.master.Util"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="common.css" type="text/css" rel="stylesheet" />
<link href="bootstrap-xtra/bootstrap.css" rel="stylesheet">
<style type="text/css">
      body {
        padding-top: 60px;
      }
</style>
<title>激活用户</title>
<script type="text/javascript" src="jquery-1.7.1.js"></script>
<script type="text/javascript">
	$(function() {
		$('.active_btn').click(function () {
			var a = $(this);
			a.attr('href', a.attr('href') + '&post_user_name=' + $('#post_user_name').val() + 
					'&post_user_password=' + $('#post_user_password').val());
			return true;
		});
	});
</script>
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
<%
	UserDao dao = Util.getUserDao();
	List<User> result = dao.getAllNotActiveUser();
%>
<div>
POST_NAME: <input id='post_user_name' type='text'/>
POST_PASSWORD: <input id='post_user_password' type='text'/>
</div>
<table style="border: solid 1px; width: 400px; margin: 10px auto;">
	<% for (User user : result) { %>
		<tr><td width="50%"><%=user.getName() %></td><td width="50%"><a class='active_btn' href='user/active?id=<%=user.getId()%>'>ACTIVE</a></td></tr>
	<% } %>
</table>
</body>
</html>