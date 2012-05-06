package com.zj.retrieval.master.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.master.DetailType;
import com.zj.retrieval.master.Node;
import com.zj.retrieval.master.NodeType;
import com.zj.retrieval.master.UserField;
import com.zj.retrieval.master.Util;
import com.zj.retrieval.master.dao.NodeDao;
import com.zj.retrieval.master.dao.UserDao;

public class AddRootNodeAction {

	private static Log log = LogFactory.getLog(AddRootNodeAction.class);
	
	private String node_name;
	private String node_name_en;
	private String uri;
	private String uri_name;
	private String desc;
	private File[] images;
	private String user_field;
	
	private String post_user_name;
	private String post_user_password;
	
	private String message;
	private boolean isError;
	
	public boolean getIsError() {
		return isError;
	}
	
	public String execute() {
		try {
			
			UserDao userDao = Util.getUserDao();
			if (!userDao.verifyUser(post_user_name, post_user_password)) {
				this.isError = true;
				this.message = "用户名或密码错误.";
				return ActionSupport.ERROR;
			}
			
			Node root = new Node();
			root.setDesc(desc);
			root.setEnglishName(node_name_en);
			root.setName(node_name);
			root.setNodeType(NodeType.NODETYPE_CLASS);
			root.setUri(uri);
			root.setUriName(root.getUri() + "#" + uri_name);
			root.setDetailType(DetailType.FULL);
			root.setParentId(Node.VIRTUAL_NODE_NAME);
			
			// 解析images
			List<String> fullPaths = new ArrayList<String>();
			String realpath = ServletActionContext.getServletContext().getRealPath("/images");
			File folder = new File(realpath);
			if(!folder.exists()) folder.mkdirs();
			if (null != images) {
				for (File srcfile : images) {
					String filename = UUID.randomUUID().toString() + ".jpg";
					File destfile = new File(folder, filename);
					FileUtils.copyFile(srcfile, destfile);
					fullPaths.add("images/" + filename);
				}
			}
			root.setImages(fullPaths);
			
			// 解析自定义字段
			if (user_field != null && !user_field.isEmpty()) {
				JSONArray user_field_jsonarray = new JSONArray(user_field);
				root.setUserfields(UserField.parse(user_field_jsonarray));
			}
			
			NodeDao nodeDao = Util.getNodeDao();
			nodeDao.addRootNode(root);
			
			this.message = "Success, o(∩_∩)o...";
			return ActionSupport.SUCCESS;
			
		} catch (JSONException e) {
			this.message = "自定义字段存在非法字符，请检查。";
			log.error("JSON字符串解析错误", e);
			return ActionSupport.ERROR;
		} catch (Exception e) {
			this.message = "发生内部逻辑错误: " + e.getMessage();
			log.error("发生内部逻辑错误", e);
			return ActionSupport.ERROR;
		}
	}

	public String getMessage() {
		return message;
	}

	public static void setLog(Log log) {
		AddRootNodeAction.log = log;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

	public void setNode_name_en(String node_name_en) {
		this.node_name_en = node_name_en;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setUri_name(String uri_name) {
		this.uri_name = uri_name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setImages(File[] images) {
		this.images = images;
	}

	public void setUser_field(String user_field) {
		this.user_field = user_field;
	}

	public void setPost_user_name(String post_user_name) {
		this.post_user_name = post_user_name;
	}

	public void setPost_user_password(String post_user_password) {
		this.post_user_password = post_user_password;
	}
}
