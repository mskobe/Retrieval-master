<%@page import="java.util.List"%>
<%@page import="com.zj.retrieval.master.Attribute"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.zj.retrieval.master.Node"%>
<%@page import="com.zj.retrieval.master.Util"%>
<%@page import="com.zj.retrieval.master.DetailType"%>
<%@page import="com.zj.retrieval.master.dao.NodeDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
<title>编辑节点</title>
<%
    int detailType;
	String nodeid = request.getParameter("node_id");
	NodeDao nodedao = Util.getNodeDao();
	Node node = nodedao.getNodeById(nodeid);
	StringBuilder imgStrBuilder = new StringBuilder();
	String imageStr = new String();
	for (String imageUrl : node.getImages()) {
		imgStrBuilder.append(Util.getImageNameExcludePath(imageUrl) + ";");
	}
	if(imgStrBuilder.length() == 0){
		imageStr = null;
	}else{
		imageStr = imgStrBuilder.substring(0, imgStrBuilder.length() - 1);
	}
	
	
%>
	<style type="text/css">
		.no_value_input {
			border-color: red;
		}
	</style>
	<script type="text/javascript" src="jquery-1.7.1.js"></script>
	<script type="text/javascript">
	var user_field_index = <%=node.getUserfields().size()%>;
	var new_attr_index = <%=node.getRetrievalDataSource().getAttributes().size()%>;

	// 删除一条用户自定义属性
	function delete_user_field(index) {
		$('#user_field_' + index).remove();
	}
	
	// 删除一条新属性
	function delete_attr(index) {
		$('#new_attr_' + index).remove();
	}
	
	// 收集用户自定义字段
	// 并填充到隐藏字段
	function process_user_field() {
		var user_fields_json = '[ ';
		
		for (var i = 0; i < user_field_index; i++) {
			var field_key_id   = 'user_field_key_'   + i;
			var field_value_id = 'user_field_value_' + i;
			
			if ($('#' + field_key_id).length == 0) {
				// 如果最后一个元素是空的，那么要把json字符串的最后一个逗号去掉
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
	}

	// 收集新特性
	// 并填充到隐藏字段
	function process_new_attr() {
		var new_attr_json = '[ ';
		
		for (var i = 0; i < new_attr_index; i++) {
			var new_attr_name_id    = 'new_attr_' + i + '_name';
			var new_attr_name_en_id = 'new_attr_' + i + '_name_en';
			var new_attr_desc_id    = 'new_attr_' + i + '_desc';
			var new_attr_image_id   = 'new_attr_' + i + '_image';
			
			// 判断该节点是否存在，以此排除因为用户手动删除的节点
			if ($('#' + new_attr_name_id).length == 0) {
				// 如果最后一个元素是空的，那么要把json字符串的最后一个逗号去掉
				if (i == (new_attr_index - 1))
					new_attr_json = new_attr_json.substr(0, new_attr_json.length - 1);
				continue;
			}
			
			var new_attr_name    = $('#' + new_attr_name_id).val();
			var new_attr_name_en = $('#' + new_attr_name_en_id).val();
			var new_attr_desc    = $('#' + new_attr_desc_id).val();
			var new_attr_image   = $('#' + new_attr_image_id).val();
			
			// 获得新属性中的自定义字段，这里使用了新的获取方法
			var new_attr_user_field_key = [];
			$('#new_attr_' + i + ' input[post_type="user_field_key"]').each(function (index) {
				new_attr_user_field_key.push($(this).val());
			});
			var new_attr_user_field_value = [];
			$('#new_attr_' + i + ' input[post_type="user_field_value"]').each(function (index) {
				new_attr_user_field_value.push($(this).val());
			});
			
			// 拼接出新属性中自定义字段的json字符串
			var new_attr_user_field_json = '[ ';
			for (var j = 0; j < new_attr_user_field_key.length; j++) {
				var one_user_filed = '{ ' +
					'key: "'   + new_attr_user_field_key[j]    + '", ' +
					'value: "' + new_attr_user_field_value[j] + '" }';
					new_attr_user_field_json += (j == (new_attr_user_field_key.length - 1) ? one_user_filed : one_user_filed + ',');
			}
			new_attr_user_field_json += ' ]';
			
			// 拼接出一条完整新属性的json字符串
			var one_field = '{' +
				'new_attr_name: "'      + new_attr_name            + '", ' +
		        'new_attr_name_en: "'   + new_attr_name_en         + '", ' +
		        'new_attr_desc: "'      + new_attr_desc            + '", ' +
		        'new_attr_image: "'     + new_attr_image           + '", ' +
		        'new_attr_user_field: ' + new_attr_user_field_json + ' }';
		    new_attr_json += (i == (new_attr_index - 1) ? one_field : one_field + ',');
		}
		new_attr_json += ' ]';
		$('#new_attr').val(new_attr_json); // 将所有新属性的json字符串赋值到表单隐藏域
	}
	
	// 判断是否存在没有填写的input域，如果有，则高亮未填写的input域并弹出提示
	function validate_input() {
		var has_no_value_input = false;
		$('input:text').each(function() {
			var input = $(this);
			if (input.val() == '') {
				if (input.attr('class') != undefined && input.attr('class').indexOf('allow_empty') != -1) {
					input.removeClass('no_value_input');
				} else {
					has_no_value_input = true;
					input.addClass('no_value_input');
				}
			} else {
				input.removeClass('no_value_input');
			}
		});
		if (has_no_value_input) {
			alert('数据未填写完整。');
			return false;
		} else {
			return true;
		}
	}
	
	function delete_self(self) {
		self.remove();
		return false;
	}
	
	// 为新属性中添加一条自定义字段
	function add_new_attr_user_field(location_id) {
		var location = $("#new_attr_" + location_id + "_user_field_location");
		var s = $("<tr><td>Key: <input type='text' post_type='user_field_key'></td><td>Value: <input type='text' post_type='user_field_value'><a href='#' onclick='delete_self($(this).parent().parent())'>DEL</a></td></tr>");
		location.after(s);
		return false;
	}
	

	$(function() {
		
		// 增加一条新属性
		$('#add_new_attr').click(function() {
			var new_attr_div = $("<table id='new_attr_" + new_attr_index + "'></table>");
			var new_attr_name = $("<tr><td>Name: </td><td><input id='new_attr_" + new_attr_index +"_name' type='text'/></td></tr>");
			var new_attr_name_en = $("<tr><td>Attr English Name: </td><td><input id='new_attr_" + new_attr_index +"_name_en' type='text'/></td></tr>");
			var new_attr_desc = $("<tr><td>Desc: </td><td><input id='new_attr_" + new_attr_index +"_desc' type='text'/></td></tr>");
			var new_attr_image = $("<tr><td>Image(Only one): </td><td><input class='allow_empty' id='new_attr_" + new_attr_index +"_image' type='text'/><a target='_blank' href='upload.jsp'>去上传文件</a></td></tr>");
			var new_attr_add_user_field = $("<tr><td colspan='2'><a href='#' onclick='add_new_attr_user_field(" + new_attr_index + ")'>ADD USER FIELD</a></td></tr>");
			var new_attr_user_filed_location = $("<tr id='new_attr_" + new_attr_index + "_user_field_location'><td colspan='2'></tr>");
			var new_attr_delete = $("<tr><td colspan='2'><a href='#' onclick='delete_attr(" + new_attr_index +")'>DELETE</a></td></tr>");
			new_attr_div.append(new_attr_name).append(new_attr_name_en)
				.append(new_attr_desc).append(new_attr_image).append(new_attr_add_user_field)
				.append(new_attr_user_filed_location).append(new_attr_delete);
			new_attr_div.attr('style', 'margin-top: 10px;border-style:solid;');
			$('#add_new_attr_location').append(new_attr_div);
			new_attr_index++;
			return false;
		});
		
		// 增加一条用户自定义属性
		$("#add_user_field").click(function() {
			var location = $("#add_user_field_location");
			var new_field = $(
				"<div style='margin-top: 10px;' id='user_field_" + user_field_index + "'>" +
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
			return false;
		});
		
		// 拦截表单的提交，为隐藏域填充值
		$("#submit_form").click(function() {
			
			// 检验表单
			if (!validate_input())
				return false;
			
			process_user_field();
			process_new_attr();
			
// 			alert('debug[隐藏字段user_field]: ' + $('#user_field').val());
// 			alert('debug[隐藏字段new_attr]: ' + $('#new_attr').val());

			// 提交表单
			$('#update_node_form').submit();
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
	<form style="width:700px;margin:0 auto;" id="update_node_form" action="node/edit" method="post" enctype="multipart/form-data" >
		<input id='node_id' type='hidden' name='node_id' value='<%=node.getId()%>'/>
		<fieldset>
          <legend>Edit Node</legend>
          <div class="clearfix">
            <label for="name">Name: </label>
            <div class="input">
              <input class="span7" id="name" name="node_name" type="text" value="<%=node.getName()%>"/>
            </div>
          </div>
          <div class="clearfix">
            <label for="en_name">English Name: </label>
            <div class="input">
              <input class="span7" id="en_name" name="node_name_en" type="text" value="<%=node.getEnglishName()%>"/>
            </div>
          </div>
          <div class="clearfix">
            <label>ParentNode ID: </label>
            <div class="input">
              <span class="uneditable-input"><%=node.getParentId()%></span>
            </div>
          </div>
          <div class="clearfix">
            <label for="desc">Description: </label>
            <div class="input">
              <input class="span7" id="desc" name="desc" type="text" value="<%=node.getDesc()%>"/>
            </div>
          </div>
          <div class="clearfix">
            <label for="uri">URI: </label>
            <div class="input">
              <input class="span7" id="uri" name="uri" type="text" value="<%=node.getUri()%>"/>
            </div>
          </div>
          <div class="clearfix">
            <label for="uri_name">URI Name: </label>
            <div class="input">
              <input class="span7" id="uri_name" name="uri_name" type="text" value="<%=node.getEnglishName()%>"/>
            </div>
          </div>
          <div class="clearfix">
            <label for="data_integrity">The Integrity of Data: </label>
            <div class="input">
              <select name="data_integrity" id="data_integrity" onchange="change_node_type();">
              <% if (node.getDetailType() == DetailType.FULL){ %>
                <option value="1" selected>Yes</option>
                <option value="2">No</option>
              <% }else { %>
                <option value="1">Yes</option>
                <option value="2" selected>No</option>
              <% } %>
              </select>
            </div>
          </div>
          <div class="clearfix">
            <label for="images">Images(多张图片请使用分号分隔): </label>
            <div class="input">
              <input class="span7" id="images" name="images" type="text" value="<%=imageStr%>"/>
              <a target='_blank' href='upload.jsp'>去上传文件</a>
            </div>
          </div>
        </fieldset>
		
<!-- 		<div style='height: 10px;'></div> -->
<!-- 		<a id='add_new_attr' href='#'>Add New Attr</a> -->
<!-- 		<div id='add_new_attr_location'> -->
			<%
// 			StringBuilder attrBuilder = new StringBuilder();
// 			int attrIndex = 0;
// 			List<Attribute> attrs = node.getRetrievalDataSource().getAttributes();
// 			for (Attribute attr : attrs) {
// 				attrBuilder.append("<table style='margin-top: 10px;border-style:solid;' id='new_attr_" + attrIndex + "'>");
// 				attrBuilder.append("<tr><td>Name: </td><td><input id='new_attr_" + attrIndex +"_name' type='text' value='" + attr.getName() + "'/></td></tr>");
// 				attrBuilder.append("<tr><td>Attr English Name: </td><td><input id='new_attr_" + attrIndex +"_name_en' type='text' value='" + attr.getEnglishName() + "'/></td></tr>");
// 				attrBuilder.append("<tr><td>Desc: </td><td><input id='new_attr_" + attrIndex +"_desc' type='text' value='" + attr.getDesc() + "'/></td></tr>");
// 				attrBuilder.append("<tr><td>Image(Only one): </td><td><input value='" + attr.getImage() + "' class='allow_empty' id='new_attr_" + attrIndex +"_image' type='text'/><a target='_blank' href='upload.jsp'>去上传文件</a></td></tr>");
// 				attrBuilder.append("<tr><td colspan='2'><a href='#' onclick='add_new_attr_user_field(" + attrIndex + ")'>ADD USER FIELD</a></td></tr>");
// 				attrBuilder.append("<tr id='new_attr_" + attrIndex + "_user_field_location'><td colspan='2'></tr>");
// 				Map<String, String> attrUserfieldMap = attr.getUserFields();
// 				for (Entry<String, String> attrUserfield : attrUserfieldMap.entrySet()) {
// 					attrBuilder.append("<tr>");
// 					attrBuilder.append("<td>Key: <input type='text' post_type='user_field_key' value='" + attrUserfield.getKey() + "'></td>");
// 					attrBuilder.append("<td>Value: <input type='text' post_type='user_field_value' value='" + attrUserfield.getValue() + "'><a href='#' onclick='delete_self($(this).parent().parent())'>DEL</a></td>");
// 					attrBuilder.append("</tr>");
// 				}
// 				attrBuilder.append("</table>");
// 				out.print(attrBuilder.toString());
// 				attrIndex++;
// 			}
			%>
<!-- 		</div> -->
		
		<div style='height: 10px;'></div>
		<a id="add_user_field" href="#">Add Your Field</a>
		<div id="add_user_field_location">
			<%
			String s = null;
			Map<String, String> userfields = node.getUserfields();
			int userfieldIndex = 0;
			for (Entry<String, String> userfieldEntry : userfields.entrySet()) {
				s = "<div style='margin-top: 10px;' id='user_field_" + userfieldIndex + "'>" +
					"<span>" +
						"key: <input id='user_field_key_" + userfieldIndex + "' type='text' value='" + userfieldEntry.getKey() + "'/>" +
					"</span>" + 
					"<span>" +
						"value: <input id='user_field_value_" + userfieldIndex + "' type='text' value='" + userfieldEntry.getValue() + "'>" +
					"</span>" +
					"<span>" +
						"<a class='user_field_delete' href='#' onclick='delete_user_field(" + userfieldIndex + ");'>DELETE</a>" +
					"</span>" +
				"</div>";
				out.print(s);
				userfieldIndex++;
			}
			%>
		</div>
		
		<input id='user_field' type="hidden" name="user_field"/>
		<input id='new_attr' type='hidden' name='new_attr'/>
		<div style="margin-top: 20px;">
			PostUserName: <input class="span3" type='text' name='post_user_name'/>
			PostUserPassword: <input class="span3" type='text' name='post_user_password'/>
		</div>
		<div><a id="submit_form" href="#">SUBMIT</a></div>
	</form>
</body>
</html>