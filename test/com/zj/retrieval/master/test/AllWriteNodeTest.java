//package com.zj.retrieval.master.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
//
//import com.zj.retrieval.master.Attribute;
//import com.zj.retrieval.master.AttributeSelectedWrongException;
//import com.zj.retrieval.master.AttributeSelector;
//import com.zj.retrieval.master.Matrix;
//import com.zj.retrieval.master.Node;
//import com.zj.retrieval.master.dao.NodeService;
//import com.zj.retrieval.master.dao.NodeServiceImpl;
//import com.zj.retrieval.master.dao.RetrievalDataSource;
//
//
//public class AllWriteNodeTest {
//
//	@Test // ok.
//	public void addRootNodeTest() {
//		
//		Node rootNode = new Node();
//		rootNode.setName("盲鳗纲");
//		rootNode.setDesc("盲鳗纲描述");
//		rootNode.setEnglishName("class_Myxini");
//		rootNode.setUri("http://202.121.64.92:5566/owl");
//		rootNode.setNodeType(Node.NODETYPE_CLASS);
//		rootNode.setLabel("盲鳗纲，位于分类顶层");
//		rootNode.setUriName(rootNode.getUri() + "#" + rootNode.getEnName());
//		
//		ClassPathXmlApplicationContext ctx = 
//			new ClassPathXmlApplicationContext("beans.xml");
//		NodeService ndService = (NodeService)ctx.getBean("nodeService");
//		
//		ndService.addRootNode(rootNode);
//	}
//	
//	@Test // ok.
//	public void getClassNodeOWLStringTest() {
//		Node node = getFullTestClassNode();
//
//		ApplicationContext ctx = new FileSystemXmlApplicationContext("war/WEB-INF/beans.xml");
//		SimpleJdbcTemplate dbopt = new SimpleJdbcTemplate((DataSource) ctx.getBean("dataSource"));
//		String owl = Node.getOwlFromNode(node, dbopt);
//		System.out.println(owl);
//	}
//	
//	@Test // ok.
//	public void getIndividualNodeOWLStringTest() {
//		Node node = new Node();
//
//		node.setName("中文名字");
//		node.setDesc("描述信息");
//		node.setParentId(1);
//		node.setEnglishName("EngilshName");
//		node.setUri("http://202.121.64.92:5566/owl");
//		node.setNodeType(Node.NODETYPE_INDIVIDUAL);
//		node.setLabel("注释信息");
//		
//
//		ClassPathXmlApplicationContext ctx = 
//			new ClassPathXmlApplicationContext("beans.xml");
//		NodeService ndService = (NodeService)ctx.getBean("nodeService");
//		
//		String owl = Node.getOwlFromNode(node, null);
//		System.out.println(owl);
//		
//	}
//	
//	@Test // ok.
//	public void addClassNode_WithoutParentAttributeSelect_WithNewAttribute_Test() {
//	
//		int PARENTNODE_ID = 7;
//		
//		Node node = new Node();
//		node.setName("新的子结炿);
//		node.setDesc("新的子结点描述信恿);
//		node.setParentId(PARENTNODE_ID);
//		node.setEnglishName("NewChildNodeEnglishName");
//		node.setUri("http://202.121.64.92:5566/owl");
//		node.setUriName(node.getUri() + "#" + node.getEnName());
//		node.setNodeType(Node.NODETYPE_CLASS);
//		node.setLabel("新的子结点_注释信息");
//		
//		ClassPathXmlApplicationContext ctx = 
//			new ClassPathXmlApplicationContext("beans.xml");
//		NodeService ndService = (NodeService)ctx.getBean("nodeService");
//		
//		Node parentNode = ndService.findNodeById(PARENTNODE_ID);
//		AttributeSelector attrSelector = ndService.getAttributeSelector(parentNode);
//		attrSelector.addNewAttribute(new Attribute("新属性——名称", "New_AttribteEnglishName", "新属性——描述"), true);
//		
//		ndService.addNode(node, parentNode, attrSelector);
//	}
//	
//	@Test // ok.
//	public void addClassNode_WithParentAttributeSelect_WithNewAttribute_Test() throws AttributeSelectedWrongException {
//		int PARENTNODE_ID = 7;
//		
//		Node node = new Node();
//		node.setName("新的子结炿);
//		node.setDesc("新的子结点描述信恿);
//		node.setParentId(PARENTNODE_ID);
//		node.setEnglishName("NewChildNodeEnglishName");
//		node.setUri("http://202.121.64.92:5566/owl");
//		node.setUriName(node.getUri() + "#" + node.getEnName());
//		node.setNodeType(Node.NODETYPE_CLASS);
//		node.setLabel("新的子结点_注释信息");
//		
//		ClassPathXmlApplicationContext ctx = 
//			new ClassPathXmlApplicationContext("beans.xml");
//		NodeService ndService = (NodeService)ctx.getBean("nodeService");
//		
//		Node parentNode = ndService.findNodeById(PARENTNODE_ID);
//		AttributeSelector attrSelector = ndService.getAttributeSelector(parentNode);
//		attrSelector.select(0, false);
//		attrSelector.addNewAttribute(new Attribute("新属性——名称", "New_AttribteEnglishName", "新属性——描述"), true);
//		
//		ndService.addNode(node, parentNode, attrSelector);
//	}
//	
//	@Test // ok.
//	public void addIndividualNodeTest() throws AttributeSelectedWrongException {
//		int PARENTNODE_ID = 7;
//		Node newNode = getFullTestClassNode();
//		newNode.setNodeType(Node.NODETYPE_INDIVIDUAL);
//		newNode.setParentId(PARENTNODE_ID);
//		
//		ClassPathXmlApplicationContext ctx = 
//			new ClassPathXmlApplicationContext("beans.xml");
//		NodeService ndService = (NodeService)ctx.getBean("nodeService");
//		
//		Node parentNode = ndService.findNodeById(PARENTNODE_ID);
//		AttributeSelector attrSelector = ndService.getAttributeSelector(parentNode);
//		attrSelector.addNewAttribute(new Attribute("新属性——名称2", "New_AttribteEnglishName", "新属性——描述2"), true);
//		attrSelector.select(0, true);
//		attrSelector.select(1, true);
//		
//		ndService.addNode(newNode, parentNode, attrSelector);
//	}
//	
//	public Node getFullTestClassNode() {
//		Node node = new Node();
//
//		node.setName("中文名字");
//		node.setDesc("描述信息");
//		node.setParentId(-1);
//		node.setEnglishName("EngilshName");
//		node.setUri("http://202.121.64.92:5566/owl");
//		node.setUriName(node.getUri() + "#" + node.getEnName());
//		node.setNodeType(Node.NODETYPE_CLASS);
//		node.setLabel("注释信息");
//		
//		List<Integer> childNodes = new ArrayList<Integer>();
//		for(int i = 0; i < 5; i++) {
//			childNodes.add(i);			
//		}
//		
//		List<Attribute> attrs = new ArrayList<Attribute>();
//		attrs.add(new Attribute("属性名称", "Attribute1_EnglishName", "属性描述信息"));
//		attrs.add(new Attribute("属性名称", "Attribute2_EnglishName", "属性描述信息"));
//		
//		Matrix matrix = new Matrix(new int[][]{
//				{1,0,0,0,0,1},
//				{1,1,1,1,1,1}
//		});
//		
//		RetrievalDataSource dataSource = new RetrievalDataSource();
//		dataSource.setAttributes(attrs);
//		dataSource.setChildNodes(childNodes);
//		dataSource.setMatrix(matrix);
//		
//		node.setRetrievalDataSource(dataSource);
//		return node;
//	}
//
//}
