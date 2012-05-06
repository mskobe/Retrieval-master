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

<script type="text/javascript" src="jquery-1.7.1.js"></script>
<script src="bootstrap-xtra/js/bootstrap-dropdown.js"></script>
<script>$(function () { prettyPrint(); });</script>
<script src="bootstrap-xtra/js/bootstrap-modal.js"></script>
<script src="bootstrap-xtra/js/bootstrap-alerts.js"></script>
<script src="bootstrap-xtra/js/bootstrap-twipsy.js"></script>
<script src="bootstrap-xtra/js/bootstrap-popover.js"></script>
<script src="bootstrap-xtra/js/bootstrap-scrollspy.js"></script>
<script src="bootstrap-xtra/js/bootstrap-tabs.js"></script>
<script src="bootstrap-xtra/js/bootstrap-buttons.js"></script>
    
<title>Retrieval</title>
</head>
<body>
  <div class="topbar-wrapper" style="z-index: 5;">
    <div class="topbar" data-dropdown="dropdown">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="#">Fishery Retrieval</a>
          <ul class="nav">
            <li class="active"><a href="index.jsp">Home</a></li>
            <li><a href="retrieval_start.jsp">Common Search</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle">Management</a>
              <ul class="dropdown-menu">
                <li><a href="add_node.jsp">Add Node</a></li>
                <li><a href="query_node.jsp">Query Node</a></li>
                <li class="divider"></li>
                <li><a href="#">Another link</a></li>
              </ul>
            </li>
            <li><a href="#about">About</a></li>
            <li><a href="contact.jsp">Contact</a></li>
          </ul>
          <p class="pull-right">Logged in as <a href="#">username</a></p>
        </div>
      </div>
    </div>
   </div>
<div class="container">
<h1>Welcome to the online Fishery Retrieval System</h1>
<table cellpadding="15px">
	<tr>
		<td><a href='add_root_node.jsp' target='_blank'>添加根节点</a></td>
		<td><a href='add_node.jsp' target='_blank'>添加节点</a></td>
		<td><a href='retrieval_start.jsp' target='_blank'>检索</a></td>
		<td><a href='upload.jsp' target='_blank'>上传文件</a></td>
	</tr>
	<tr>
		<td><a href='add_user.jsp' target='_blank'>用户注册</a></td>
		<td><a href='active_user.jsp' target='_blank'>管理员激活用户</a></td>
		<td><a href='query_node.jsp' target='_blank'>管理员管理节点</a></td>
		<td><a href='view_remote_node_detail.jsp' target='_blank'>远程查看</a></td>
	</tr>
	<tr>
		<td><a href='add_node_brief.jsp' target='_blank'>添加远程节点</a></td>
		<td></td>
		<td></td>
	</tr>
</table>
</div>
</body>
</html>