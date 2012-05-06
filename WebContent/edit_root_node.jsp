<%@page import="java.util.Map.Entry"%>
<%@page import="com.zj.retrieval.master.Util"%>
<%@page import="com.zj.retrieval.master.Node"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="bootstrap-xtra/bootstrap.css" rel="stylesheet">
<style type="text/css">
      body {
        padding-top: 60px;
      }
</style>
<%
	String nodeid = request.getParameter("node_id");
	Node nd = Util.getNodeDao().getNodeById(nodeid);
	StringBuilder imagesBuilder = new StringBuilder();
	for (String imageUrl : nd.getImages()) {
		imagesBuilder.append(Util.getImageNameExcludePath(imageUrl) + ";");
	}
	String imagesStr = imagesBuilder.substring(0, imagesBuilder.length() - 1);
%>
<title>编辑根物种</title>
	<link href="common.css" type="text/css" rel="stylesheet" />
		<style type="text/css">
		.no_value_input {
			border-color: red;
		}
	</style>
	<script type="text/javascript" src="jquery-1.7.1.js"></script>
	<script type="text/javascript">
	var user_field_index = <%=nd.getUserfields().size()%>;

	// 删除一条用户自定义属性
	function delete_user_field(index) {
		$('#user_field_' + index).remove();
	}

	// 删除一张图片的上传field
	function delete_image_field(index) {
		$('#image_field_' + index).remove();
	}

	$(function() {
		
		// 增加一条用户自定义属性
		$("#add_user_field").click(function() {
			var location = $("#add_user_field_location");
			var new_field = $(
				"<div id='user_field_" + user_field_index + "'>" +
					"<span>" +
						"key: <input id='user_field_key_" + user_field_index + "' type='text'/>" +
					"</span>" + 
					"<span>" +
						"value: <input id='user_field_value_" + user_field_index + "' type='text'>" +
					"</span>" +
					"<span>" +
						"<a class='user_field_delete' href='#' onclick='delete_user_field(" + user_field_index + ");'>DELETE</a>" +
					"</span>" +
				"</div>"
			);
			location.append(new_field);
			user_field_index++;
		});
		
		// 增加一张图片
		$("#add_image").click(function() {
			var location = $('#add_image_location');
			var new_file_field = $(
				"<div id='image_field_" + image_index + "'>" +
					"<input type='file' name='images' accept='image/jpeg'/>" +
					"<span>" +
						"<a href='#' onclick='delete_image_field(" + image_index + ");'>DELETE</a>" +
					"</span>" +
				"</div>"
				);
				location.append(new_file_field);
				image_index++;
		});
		
		// 拦截表单的提交，为隐藏域填充值
		$("#submit_form").click(function() {
			
			// 检验表单
			var has_no_value_input = false;
			$('input:text').each(function() {
				if ($(this).val() == '') {
					has_no_value_input = true;
					$(this).addClass('no_value_input');
				} else {
					$(this).removeClass('no_value_input');
				}
			});
			if (has_no_value_input) {
				alert('数据未填写完整。');
				return false;
			}
			
			var user_fields_json = '[ ';
			
			for (var i = 0; i < user_field_index; i++) {
				var field_key_id   = 'user_field_key_'   + i;
				var field_value_id = 'user_field_value_' + i;
				
				if ($('#' + field_key_id).length == 0) {
					if (i == (user_field_index - 1)) 
						user_fields_json = user_fields_json.substr(0, user_fields_json.length - 1);
					continue;
				}
				var key = $('#' + field_key_id).val();
				var value = $('#' + field_value_id).val();
				
				var one_field = '{key: "' + key + '", value: "' + value + '"}';
				user_fields_json += (i == (user_field_index - 1) ? one_field : one_field + ',');
			}
			user_fields_json += ' ]';
			$('#user_field').val(user_fields_json);
			// 提交表单
			$('#edit_node_form').submit();
// 			alert($('#user_field').val()); // for test
			return false;
		});



	}); // end of $(function() {
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
	<form id="edit_node_form" action="root-node/edit" method="post" enctype="multipart/form-data" >
		<input id='node_id' type='hidden' name='node_id' value='<%=nd.getId()%>'/>
		<table id="base_info">
			<tr><td>Name: </td><td><input name="node_name" type="text" value='<%=nd.getName()%>'/></td></tr>
			<tr><td>English Name: </td><td><input name="node_name_en" type="text" value='<%=nd.getEnglishName()%>'/></td></tr>
			<tr><td>Description: </td><td><input name="desc" type="text" value='<%=nd.getDesc()%>'/></td></tr>
			<tr><td>URI:</td><td><input name="uri" type="text" value='<%=nd.getUri()%>'/></td></tr>
			<tr><td>URI Name: </td><td><input name="uri_name" type="text" value='<%=nd.getEnglishName()%>'/></td></tr>
			<tr><td>Images: </td><td><input name="images" type='text' value='<%=imagesStr%>'><a target='_blank' href='upload.jsp'>去上传文件</a></td></tr>
		</table>
		
		<a id="add_user_field" href="#">Add Your Field</a>
		<div id="add_user_field_location">
			<!--
			var location = $("#add_user_field_location");
			var new_field = $(
				"<div id='user_field_" + user_field_index + "'>" +
					"<span>" +
						"key: <input id='user_field_key_" + user_field_index + "' type='text'/>" +
					"</span>" + 
					"<span>" +
						"value: <input id='user_field_value_" + user_field_index + "' type='text'>" +
					"</span>" +
					"<span>" +
						"<a class='user_field_delete' href='#' onclick='delete_user_field(" + user_field_index + ");'>DELETE</a>" +
					"</span>" +
				"</div>"
			);
			location.append(new_field);
			user_field_index++;
			-->
		<%
			int userfieldIndex = 0;
			for (Entry<String, String> userfieldEntry : nd.getUserfields().entrySet()) {
		%>
				<div id='user_field_<%=userfieldIndex%>'>
					<span>
						Key: <input id='user_field_key_<%=userfieldIndex%>' type='text' value='<%=userfieldEntry.getKey()%>'/>
					</span>
					<span>
						Value: <input id='user_field_value_<%=userfieldIndex%>' type='text' value='<%=userfieldEntry.getValue()%>'>
					</span>
					<span>
						<a class='user_field_delete' href='#' onclick='delete_user_field(<%=userfieldIndex%>);'>DELETE</a>
					</span>
				</div>
		<%
			}
		%>
		</div>
		
		<input id='user_field' type="hidden" name="user_field"/>
		PostUserName: <input type='text' name='post_user_name'/>
		PostUserPassword: <input type='text' name='post_user_password'/>
		<div><a id="submit_form" href="#">SUBMIT</a></div>
	</form>
</body>
</html>