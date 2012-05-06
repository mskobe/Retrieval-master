//package com.zj.retrieval.master.test;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.zj.retrieval.master.Attribute;
//import com.zj.retrieval.master.Matrix;
//import com.zj.retrieval.master.Node;
//import com.zj.retrieval.master.NodeRetrieval;
//import com.zj.retrieval.master.RetrievalResult;
//import com.zj.retrieval.master.dao.RetrievalDataSource;
//
//public class RetrievalTest {
//	
//	@Test
//	public void retrievalTest() throws Exception {
//		Node node = new Node();
//
//		List<Integer> childNodes = new ArrayList<Integer>();
//		childNodes.add(72);
//		childNodes.add(12);
//		childNodes.add(88);
//		childNodes.add(56);
//		childNodes.add(30);
//		
//		List<Attribute> attrs = new ArrayList<Attribute>();
//		attrs.add(new Attribute("属�?1_名称", "Attribute1_EnglishName", "属�?1描述信息"));
//		attrs.add(new Attribute("属�?2_名称", "Attribute2_EnglishName", "属�?2描述信息"));
//		attrs.add(new Attribute("属�?3_名称", "Attribute3_EnglishName", "属�?3描述信息"));
//		attrs.add(new Attribute("属�?4_名称", "Attribute4_EnglishName", "属�?4描述信息"));
//		attrs.add(new Attribute("属�?5_名称", "Attribute5_EnglishName", "属�?5描述信息"));
//		
//		Matrix matrix = new Matrix(new int[][]{
//				{1,0,0,0,0},
//				{2,0,1,0,0},
//				{2,1,2,0,0},
//				{2,2,2,1,0},
//				{2,2,2,2,1}
//		});
//		
//		RetrievalDataSource dataSource = new RetrievalDataSource();
//		dataSource.setAttributes(attrs);
//		dataSource.setChildNodes(childNodes);
//		dataSource.setMatrix(matrix);
//		node.setRetrievalDataSource(dataSource);
//		
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
//		NodeRetrieval nr = (NodeRetrieval)ctx.getBean("nodeRetrieval");
//		nr.setRetrievalNode(node);
//		
//		String selectState = "7";
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		RetrievalResult result = null;
//		while(!(result = nr.retrieval(selectState)).hasResult()) {
//			System.out.println(result.getNext());
//			String answer = in.readLine().toLowerCase();
//			if (answer.equals("yes")) 
//				answer = String.valueOf(Attribute.YES);
//			else if (answer.equals("no"))
//				answer = String.valueOf(Attribute.NO);
//			else
//				answer = String.valueOf(Attribute.UNKNOW);
//			selectState = selectState.concat(answer);
//		}
//		System.out.println("\n\n�?��结束:");
//		if (result.getResult().size() == 0)
//			System.out.println("没有符合要求的子结点");
//		else {
//			for(Integer nodeId : result.getResult()) {
//				System.out.println("节点ID�? + nodeId);
//			}
//		}
//	}
//}
