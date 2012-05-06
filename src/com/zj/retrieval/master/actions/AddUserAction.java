package com.zj.retrieval.master.actions;

import java.util.UUID;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.master.User;
import com.zj.retrieval.master.Util;
import com.zj.retrieval.master.dao.UserDao;

public class AddUserAction {
	private String name;
	private String password;
	private boolean isError;
	private String message;
	
	public String execute() {
		try {
			UserDao userDao = Util.getUserDao();
			if (userDao.getUserByName(name).size() > 0) {
				this.isError = true;
				this.message = String.format("用户名：%1$s 已经存在！", name);
				return ActionSupport.ERROR;
			}
			User user = new User();
			user.setName(name);
			user.setId(UUID.randomUUID().toString());
			user.setPassword(password);
			user.setIsActive(0);
			userDao.addUser(user);
			
			this.message = "添加成功 o(∩_∩)o...";
			this.isError = false;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
