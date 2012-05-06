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
	<title>添加物种（非详细）</title>
	<style type="text/css">
		.no_value_input {
			border-color: red;
		}
	</style>
	<script type="text/javascript" src="jquery-1.7.1.js"></script>
	<script type="text/javascript">
	var new_attr_index = 0;

	// 删除一条新属性
	function delete_attr(index) {
		$('#new_attr_' + index).remove();
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
			var new_attr_image = $("<tr><td>Image: </td><td><input class='allow_empty' id='new_attr_" + new_attr_index +"_image' type='text'/></td></tr>");
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
		
		// 拦截表单的提交，为隐藏域填充值
		$("#submit_form").click(function() {
			
			// 检验表单
			if (!validate_input())
				return false;
			
			process_new_attr();
			
// 			alert('debug[隐藏字段new_attr]: ' + $('#new_attr').val());

			// 提交表单
			$('#add_node_form').submit();
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
	<form style="width:700px;margin:0 auto;" id="add_node_form" action="briefnode/add" method="post" enctype="multipart/form-data" >
		<table id="base_info">
			<tr><td>ID: </td><td><input name="node_id" type="text"/></td></tr>
			<tr><td>Parent ID: </td><td><input name="parent_id" type="text"/></td></tr>
			<tr><td>Name: </td><td><input name="node_name" type="text"/></td></tr>
			<tr><td>English Name: </td><td><input name="node_name_en" type="text"/></td></tr>
			<tr><td>Parents Attr: </td><td><input class='allow_empty' name='parent_attr' type='text'/></td></tr>
			<tr><td>联系方式: </td><td><input name='contact' type='text'/></td></tr>
		</table>
		
		<div style='height: 10px;'></div>
		<a id='add_new_attr' href='#'>Add New Attr</a>
		<div id='add_new_attr_location'>
			<!--
				<div id='new_attr_x'> 
					<div>Attr Name: <input id='new_attr_x_name' type='text'/></div>
					<div>Attr English Name: <input id='new_attr_x_name_en' type='text'/></div>
					<div>Desc: <input id='new_attr_x_desc' type='text'/></div>
					<div>Image: <input id='new_attr_x_image' type='text'/></div>
					<div><a href='#' onclick='delete_attr(x)'>DELETE</a></div>
				</div>
			-->
		</div>
		
		<input id='new_attr' type='hidden' name='new_attr'/>
		<div style="margin-top: 20px;">
			PostUserName: <input type='text' name='post_user_name'/>
			PostUserPassword: <input type='text' name='post_user_password'/>
		</div>
		<div><a id="submit_form" href="#">SUBMIT</a></div>
	</form>
</body>
</html>