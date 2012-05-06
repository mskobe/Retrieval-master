//package com.zj.retrieval.master.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//import com.zj.retrieval.master.Attribute;
//import com.zj.retrieval.master.Matrix;
//import com.zj.retrieval.master.Node;
//import com.zj.retrieval.master.dao.NodeService;
//import com.zj.retrieval.master.dao.NodeServiceImpl;
//import com.zj.retrieval.master.dao.RetrievalDataSource;
//
//
//public class AllReadNodeTest {
//	@Test
//	public void getRetrievalDataSourceTest() throws Exception {
//		Node node = new Node();
//
//		node.setName("中文名字");
//		node.setDesc("描述信息");
//		// 标注�?1可以避免调用数据库去查询父节点的信息
//		node.setParentId(-1);
//		node.setEnglishName("EngilshName");
//		node.setUri("http://202.121.64.92:5566/owl");
//		node.setUriName(node.getUri() + "#" + node.getEnName());
//		node.setNodeType(Node.NODETYPE_CLASS);
//		node.setLabel("注释信息");
//		
//		List<Integer> childNodes = new ArrayList<Integer>();
//		for(int i = 0; i < 4; i++) {
//			childNodes.add(i);			
//		}
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
//				{1,1,0,0,0},
//				{1,1,1,0,0},
//				{1,1,1,1,0}
//		});
//		
//		RetrievalDataSource dataSource = new RetrievalDataSource();
//		dataSource.setAttributes(attrs);
//		dataSource.setChildNodes(childNodes);
//		dataSource.setMatrix(matrix);
//		
//		node.setRetrievalDataSource(dataSource);
//		
//		NodeServiceImpl ndService = new NodeServiceImpl();
//		String owl = Node.getOwlFromNode(node, null);
//		System.out.println(owl);
//		
//		Node resolvedNode = new Node();
//		ndService.resolveOWLtoNode(resolvedNode);
//		
//	}
//}
