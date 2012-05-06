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
<title>注册用户</title>
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
<form action='user/add' method='post'>
	<table>
	<tr><td>用户名：</td><td><input type='text' name='name'/></td></tr>
	<tr><td>密码：</td><td><input type="password" name='password'/></td></tr>
	<tr><td colspan='2'><input type='submit' value='注册'/></td></tr>
	</table>
</form>
</body>
</html>