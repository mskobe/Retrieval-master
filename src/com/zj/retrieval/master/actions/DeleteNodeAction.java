package com.zj.retrieval.master.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.master.Node;
import com.zj.retrieval.master.Util;
import com.zj.retrieval.master.dao.NodeDao;
import com.zj.retrieval.master.dao.UserDao;

public class DeleteNodeAction {

	private static Log log = LogFactory.getLog(AddNodeAction.class);
	private String node_id;
	private String message;
	private boolean isError;
	
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
			
			Node nd = new Node();
			nd.setId(node_id);
			log.info("要删除的节点id为：" + nd.getId());
			
			NodeDao ndService =  Util.getNodeDao();
			
			if (ndService.deleteNode(nd)) {
				this.isError = false;
				this.message = "Success, o(∩_∩)o...";
				return ActionSupport.SUCCESS;
			} else {
				this.isError = true;
				this.message = "Fail.";
				return ActionSupport.ERROR;
			}
			
		} catch (Exception ex) {
			log.error("在删除结点时发生未知错误", ex);
			this.isError = true;
			this.message = "Fail.";
			return ActionSupport.ERROR;
		}
	}
	public String getMessage() {
		return message;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
	public boolean getIsError() {
		return this.isError;
	}
	public void setPost_user_name(String post_user_name) {
		this.post_user_name = post_user_name;
	}
	public void setPost_user_password(String post_user_password) {
		this.post_user_password = post_user_password;
	}

}
