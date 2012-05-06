<%@page import="com.zj.retrieval.master.DetailType"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
	import="com.zj.retrieval.master.dao.NodeDao"
	import="com.zj.retrieval.master.Util"
	import="java.util.List"
	import="com.zj.retrieval.master.Node"
%>
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
<script type="text/javascript">
	function delete_node(id) {
		if ( $('#post_user_name').val().length == 0 || 
			 $('#post_user_password').val().length == 0) {
			alert('请首先输入管理员用户名和密码。');
			return false;
		}
		$.ajax({
			type: 'post',
			url: 'node/delete',
			success: function (data, textStatus, jqXHR) {
				if (data.isError) {
					alert("没有删除成功: " + data.message);
				} else {
					alert("删除成功: " + data.message);
					$('#' + id).hide('slow', function() {
						$('#' + id).remove();
					});
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
				alert('ajax error: ' + textStatus);
			},
			data: { 
				'node_id': id, 
				'post_user_name': $('#post_user_name').val(), 
				'post_user_password': $('#post_user_password').val() },
			dataType: 'json'
		});
		return false;
	}
</script>
<title>查询物种</title>
</head>
<body>
  <div class="topbar-wrapper" style="z-index: 5;">
    <div class="topbar" data-dropdown="dropdown">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="#">Fishery Retrieval</a>
          <ul class="nav">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="retrieval_start.jsp">Common Search</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle">Management</a>
              <ul class="dropdown-menu">
                <li><a href="add_node.jsp">Add Node</a></li>
                <li class="active"><a href="query_node.jsp">Query Node</a></li>
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
<div>
	UserID: <input id='post_user_name' type="text" name='post_user_id'/>
	UserPassword: <input id='post_user_password' type='password' name='post_user_password'>
</div>
<table width="100%">
	<tr><td>编号</td><td>名称</td><td>父节点ID</td><td>信息是否完整</td><td><!-- DELETE --></td><td><!-- View Detail --></td><td><!-- EDIT --></td></tr>
<%
	NodeDao nodeDao = Util.getNodeDao();
	List<Node> nodes = nodeDao.getAllNodeAsBrief();
	for (Node nd : nodes) {
		out.print(String.format(
				"<tr id='%1$s'>" +
					"<td>%1$s</td>" +
					"<td>%2$s</td>" + 
					"<td>%3$s</td>" +
					"<td>%4$s</td>" +
					"<td><a href='#' onclick=\"delete_node('%1$s')\">DELETE</a></td>" +
					"<td><a target='_blank' href='view_node_detail.jsp?node_id=%1$s'>View Detail</a></td>" + 
					"<td><a target='_blank' href='%5$s?node_id=%1$s'>EDIT</a></td>" +
				"</tr>", 
			nd.getId(), nd.getName(), nd.getParentId(), 
			nd.getDetailType() == DetailType.FULL ? "是" : "否", 
			nd.getParentId().equals("virtual_node") ? "edit_root_node.jsp" : "edit_node.jsp"));
	}
%>
</table>
</body>
</html>