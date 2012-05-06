package com.zj.retrieval.master.actions;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.master.Util;
import com.zj.retrieval.master.dao.UserDao;

public class ActiveUserAction {
	private String id;
	private boolean isError;
	private String message;
	
	private String post_user_name;
	private String post_user_password;
	
	public String execute() {
		try {
			UserDao userDao = Util.getUserDao();
			if (!userDao.verifySu(post_user_name, post_user_password)) {
				this.isError = true;
				this.message = "用户名或密码错误.";
				return ActionSupport.ERROR;
			}
			
			userDao.activeUser(id);
			
			this.isError = false;
			this.message = "激活成功o(∩_∩)o...";
			return ActionSupport.SUCCESS;
		} catch (Exception ex) {
			this.isError = true;
			this.message = ex.getMessage();
			return ActionSupport.ERROR;
		}
	}

	public boolean getIsError() {
		return isError;
	}

	public String getMessage() {
		return message;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPost_user_name(String post_user_name) {
		this.post_user_name = post_user_name;
	}

	public void setPost_user_password(String post_user_password) {
		this.post_user_password = post_user_password;
	}
}
