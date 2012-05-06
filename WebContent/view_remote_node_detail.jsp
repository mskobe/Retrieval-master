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
<title>查看远程物种信息</title>
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
	<form action="remote_node_detail.jsp">
		<table>
			<tr><td>URL: </td><td><input name='url' type="text" style="width: 600px;"/></td></tr>
			<tr><td>NODE ID: </td><td><input name='node_id' type='text'/></td></tr>
			<tr><td>USER NAME: </td><td><input name='user_name' type="text"/></td></tr>
			<tr><td>USER PWD: </td><td><input name='user_pwd' type='text'/></td></tr>
			<tr><td colspan='2'><input value='VIEW' type='submit'/></td></tr>
			<tr><td></td><td></td></tr>
		</table>
	</form>
</body>
</html>