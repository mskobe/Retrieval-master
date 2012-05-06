<%@page import="com.zj.retrieval.master.Node"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.zj.retrieval.master.RetrievalResult"%>
<%@page import="com.zj.retrieval.master.dao.NodeDao"%>
<%@page import="com.zj.retrieval.master.Util"%>
<%@page import="com.zj.retrieval.master.dao.NodeDao"%>

<%@page language="java" 
		contentType="text/html; charset=utf-8"
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
<title>检索</title>
<%
	NodeDao nodeDao = Util.getNodeDao();
	Node root = nodeDao.getNodeById(Node.VIRTUAL_NODE_NAME);
	List<String> ids = root.getRetrievalDataSource().getChildNodes();
%>
<script type="text/javascript" src='jquery-1.7.1.js'></script>
</head>
<body>

<div class="topbar-wrapper" style="z-index: 5;">
    <div class="topbar" data-dropdown="dropdown">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="#">Fishery Retrieval</a>
          <ul class="nav">
            <li><a href="index.jsp">Home</a></li>
            <li class="active"><a href="retrieval_start.jsp">Common Search</a></li>
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
	<table>
		<tr><td>ID</td><td>中文名称</td><td>英文名称</td><td></td></tr>
		<% for (String id : ids) { Node nd = nodeDao.getNodeById(id); %>
		<tr>
			<td><%=nd.getId()%></td>
			<td><%=nd.getName()%></td>
			<td><%=nd.getEnglishName()%></td>
			<td><a href='retrieval.jsp?node_id=<%=nd.getId()%>'>RETRIEVAL FROM HERE</a><td>
		</tr>			
		<% } %>
	</table>
  </div>

</body>
</html>