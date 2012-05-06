package com.zj.retrieval.master.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zj.retrieval.master.Node;
import com.zj.retrieval.master.dao.NodeDao;

public class TestDeleteNode {
	@Test
	public void deleteNodeTest() {
		ApplicationContext ctx = 
			new ClassPathXmlApplicationContext("beans.xml");
		NodeDao ndService = (NodeDao)ctx.getBean("nodeService");
		
//		Node nd = new Node();
//		nd.setId(19);
//		ndService.deleteNode(nd);
	}
}
