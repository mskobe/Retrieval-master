package com.zj.retrieval.master.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.master.Node;
import com.zj.retrieval.master.Util;
import com.zj.retrieval.master.dao.NodeDao;

public class QueryNodeOWLAction {

	private static Log log = LogFactory.getLog(QueryNodeOWLAction.class);
	
	private String format;
	private String node_id;
	private String owl;
	
	protected String execute() {
		try {
			
			log.info("查询owl的节点id为：" + node_id);
			
			NodeDao ndService = Util.getNodeDao();
			
			Node node = ndService.getNodeById(node_id);
			
			this.owl = Boolean.valueOf(format) ? XMLUtil.format(node.getOwl(), 4) : node.getOwl();
			return ActionSupport.SUCCESS;
			
		} catch (Exception ex) {
			log.error("获得OWL时发生错误", ex);
			return ActionSupport.ERROR;
		}
		
	}

	public String getOwl() {
		return owl;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
}
