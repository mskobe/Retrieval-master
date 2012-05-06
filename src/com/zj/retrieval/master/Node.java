package com.zj.retrieval.master;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.jamesmurty.utils.XMLBuilder;
import com.zj.retrieval.master.dao.RetrievalDataSource;

public class Node {
	private static Log log = LogFactory.getLog(Node.class);
		
	public final static String VIRTUAL_NODE_NAME = "virtual_node";
	
	private String uri = "";
	private String id = "";
	private String name = "";
	private String uriName = "";
	private String englishName = "";
	private String desc = "";
	private String parentId = "";
	private String owl = "";
	private RetrievalDataSource retrievalDataSource;
	private String label = "";
	private int nodeType = NodeType.NODETYPE_CLASS;
	private int detailType = DetailType.FULL;
	private String contact;
	private List<String> images = new ArrayList<String>();
	private Map<String, String> userfields = new HashMap<String, String>();
	
	public Node() {
		retrievalDataSource = new RetrievalDataSource();
	}
	public void setImagesStr(String str) {
		if (str == null || str.isEmpty()) {
			images = new ArrayList<String>();
		} else {
			String[] splited = str.split(";");
			images = Arrays.asList(splited);
		}
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUriName() {
		return uriName;
	}
	public void setUriName(String uriName) {
		this.uriName = uriName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String enName) {
		this.englishName = enName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getOwl() {
		return owl;
	}
	public void setOwl(String owl) {
		this.owl = owl;
	}
	public RetrievalDataSource getRetrievalDataSource() {
		return retrievalDataSource;
	}
	public void setRetrievalDataSource(RetrievalDataSource retrievalDataSource) {
		this.retrievalDataSource = retrievalDataSource;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getNodeType() {
		return nodeType;
	}
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}
	public List<String> getImages() {
		return images;
	}
	public String getImagesStr() {
		if (images == null || images.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		for (String image : images) {
			sb.append(image + ";");
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}
	public void setImages(List<String> image) {
		this.images = image;
	}
	
	public static Node parseVirtualNodeFromOWL(String owl) throws Exception {
		Node result = new Node();
		XMLBuilder builder = XMLBuilder.parse(new InputSource(new StringReader(owl)));
		Element elem = builder.xpathFind("/RDF/Class/childNodes").getElement();
		for(int i = 0; i < elem.getChildNodes().getLength(); i++) {
			Element elemNode = (Element)elem.getChildNodes().item(i);
			String text = elemNode.getTextContent();
			result.getRetrievalDataSource().getChildNodes().add(text);
		}
		log.info("解析出VirtualNode的子结点列表" + result.getRetrievalDataSource().getChildNodes());
		return result;
	}
	public static void parseNodeFromOWL(Node nd) throws Exception {
		try {
			
			if (nd.getOwl() == null || nd.getOwl().equals("")) {
				nd.setDesc("");
				RetrievalDataSource rds = new RetrievalDataSource();
				rds.setAttributes(new ArrayList<Attribute>());
				rds.setChildNodes(new ArrayList<String>());
				rds.setMatrix(new Matrix());
				nd.setRetrievalDataSource(rds);
				return;
			}
			
			XMLBuilder builder = XMLBuilder.parse(new InputSource(new StringReader(nd.getOwl())));
			
//			// 解析节点类型
//			int nodeType = -1;
//			try {
//				nodeType = Integer.valueOf(builder.xpathFind("/RDF/Class/nodeType").getElement().getTextContent());
//			} catch (XPathExpressionException e) {
//				//throw new Exception("不存在节点/RDF/Class/nodeType", e);
//			}
			
			// 解析Attribute
			List<Attribute> attrs = new ArrayList<Attribute>();
			try {
				NodeList attributesElements = builder.xpathFind("/RDF/Class/attributes").getElement().getChildNodes();
				for(int i = 0; i < attributesElements.getLength(); i++) {
					Element attrElement = (Element)attributesElements.item(i);
					int attrIndex = Integer.valueOf(attrElement.getAttribute("index"));
					String attrEnName = attrElement.getAttribute("enName");
					String attrName = attrElement.getAttribute("name");
					String attrDesc = attrElement.getElementsByTagName("desc").item(0).getTextContent();
					String attrImage = attrElement.getElementsByTagName("image").item(0).getTextContent();
					
					Attribute attr = new Attribute(attrName, attrEnName, attrDesc, attrImage);
					
					NodeList fields = attrElement.getElementsByTagName("field");
					Map<String, String> attrUserfields = new HashMap<String, String>();
					for (int j = 0; j < fields.getLength(); j++) {
						Element field = (Element) fields.item(j);
						attrUserfields.put(field.getAttribute("key"), field.getTextContent());
					}
					attr.setUserFields(attrUserfields);
					attrs.add(attrIndex, attr);
				}
			} catch (XPathExpressionException e) {
				log.info("不存在节点/RDF/Class/attributes");
			}
			
			// 解析Matrix
			Matrix matrix = new Matrix();
			try {
				NodeList rowElements = builder.xpathFind("/RDF/Class/matrix").getElement().getChildNodes();
				int rowSize = rowElements.getLength();
				int colSize = rowSize == 0 ? 0 : rowElements.item(0).getTextContent().length();
				for(int rowIndex = 0; rowIndex < rowSize; rowIndex++) {
					int[] row = new int[colSize];
					String rowString = rowElements.item(rowIndex).getTextContent();
					for(int dig = 0; dig < colSize; dig++) {
						row[dig] = Integer.valueOf(rowString.substring(dig, dig + 1));
					}
					matrix.addRow(row, 0, row.length);
				}
			} catch (XPathExpressionException e) {
				log.info("不存在节点/RDF/Class/matrix");
			}

			// 解析ChildList
			List<String> child_nodes = new ArrayList<String>();
			try {
				NodeList nodeElements = builder.xpathFind("/RDF/Class/childNodes").getElement().getChildNodes();
				for(int i = 0; i < nodeElements.getLength(); i++) {
					String child_node_id = nodeElements.item(i).getTextContent();
					child_nodes.add(child_node_id);
				}
			} catch (XPathExpressionException e) {
				log.info("不存在节点/RDF/Class/childNodes");
			}
			
			// 解析userfields
			Map<String, String> user_fields = new HashMap<String, String>();
			try {
				NodeList nodeFields = builder.xpathFind("/RDF/Class/userfields").getElement().getChildNodes();
				for (int i = 0; i < nodeFields.getLength(); i++) {
					String key = ((Element) nodeFields.item(i)).getAttribute("key");
					String value = nodeFields.item(i).getTextContent();
					user_fields.put(key, value);
				}
			} catch (XPathExpressionException e) {
				log.info("不存在节点/RDF/Class/userfields");
			}
			
			// 解析Desc
			String desc = "";
			try {
				desc = builder.xpathFind("/RDF/Class/desc").getElement().getTextContent();
			} catch(XPathExpressionException e) {
				log.info("不存在节点/RDF/Class/desc, desc将保持为空");
			}
			
			RetrievalDataSource data_source = new RetrievalDataSource();
			data_source.setAttributes(attrs);
			data_source.setMatrix(matrix);
			data_source.setChildNodes(child_nodes);
			
			nd.setRetrievalDataSource(data_source);
			nd.setDesc(desc);
			nd.setUserfields(user_fields);
			
		} catch (Exception e) {
			log.error("解析OWL时出错", e);
			throw new Exception("解析OWL时出错@NodeServiceImpl.getRetrievalDataSource()", e);
		}
	
	}
	public static String getOwlFromNode(Node nd, SimpleJdbcTemplate jdbcOperations) {

		String result = null;
		try {
			
			XMLBuilder builder = XMLBuilder.create("rdf:RDF");
			
			builder.a("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
			builder.a("xmlns:owl", "http://www.w3.org/2002/07/owl#");
			builder.a("xmlns:rdfs", "http://www.w3.org/2000/01/rdf-schema#");
			builder.a("xmlns:xsd", "http://www.w3.org/2001/XMLSchema#");
			
			// 创建class类型节点的owl格式
			if(nd.getNodeType() == NodeType.NODETYPE_CLASS) {
				// Create <owl:Class>
				String parentURI = nd.getParentId() == Node.VIRTUAL_NODE_NAME ? "" : getParentUri(nd.getParentId(), jdbcOperations);
				XMLBuilder elemClass = builder.e("owl:Class").a("rdf:ID", nd.getUri() + "#" + nd.getEnglishName());
				elemClass.e("rdfs:subClassOf").a("rdf:resource", parentURI);
				elemClass.e("rdfs:label").t(nd.getLabel());
				
				// Create <desc>
				elemClass.e("desc").t(nd.getDesc());
				
				// Create <images>
				XMLBuilder eImages = elemClass.e("images");
				for (String image_path : nd.getImages()) {
					eImages.e("item").t(image_path);
				}
				
				// Create <userfields>
				XMLBuilder elemUserfields = elemClass.e("userfields");
				Map<String, String> userfields = nd.getUserfields();
				for (String key : userfields.keySet()) {
					elemUserfields.e("field").a("key", key).t(userfields.get(key));
				}
				
				// Create <attributes>
				List<Attribute> attrs = nd.getRetrievalDataSource().getAttributes();
				XMLBuilder elemAttributes = elemClass.e("attributes");
				for(int index = 0; index < attrs.size(); index ++) {
					XMLBuilder elemAttribute = elemAttributes.e("attribute");
					elemAttribute.a("name", attrs.get(index).getName())
						         .a("enName", attrs.get(index).getEnglishName())
						         .a("index", String.valueOf(index))
							         .e("desc").t(attrs.get(index).getDesc()).up()
							         .e("image").t(attrs.get(index).getImage());
					Map<String, String> attrUserfields = attrs.get(index).getUserFields();
					XMLBuilder elemAttrUserfields = elemAttribute.element("userfields");
					for (String key : attrUserfields.keySet()) {
						elemAttrUserfields.e("field").a("key", key).t(attrUserfields.get(key));
					}
				}

				// Create <matrix>
				XMLBuilder elemMatrix = elemClass.e("matrix");
				Matrix matrix = nd.getRetrievalDataSource().getMatrix();
				for(int rowindex = 0; rowindex < matrix.getRowSize(); rowindex++) {
					int[] row = matrix.getRow(rowindex);
					elemMatrix.e("row").a("index", String.valueOf(rowindex)).t(integerArray2String(row));
				}
				
				// Create <childNodes>
				XMLBuilder elemChildNodes = elemClass.e("childNodes");
				List<String> childNodes = nd.getRetrievalDataSource().getChildNodes();
				for(int index = 0; index < childNodes.size(); index++) {
					elemChildNodes.e("node").a("index", String.valueOf(index))
						.t(childNodes.get(index));
				}
			} // end of if(nd.getNodeType() == Node.NODETYPE_CLASS) {
			
			// 创建individual类型节点的owl格式
			if(nd.getNodeType() == NodeType.NODETYPE_INDIVIDUAL) {
				String parentEnName = jdbcOperations.queryForObject(
						"select `enName` from fishes where id=?",
						java.lang.String.class, nd.getParentId());
				XMLBuilder individual = builder.e(parentEnName);
				individual.a("rdf:ID", nd.getUri() + "#" + nd.getEnglishName())
					.e("label").t(nd.getLabel()).up()
					.e("name").t(nd.getName()).up()
					.e("desc").t(nd.getDesc());

				XMLBuilder eImages = individual.e("images");
				for (String image_path : nd.getImages()) {
					eImages.e("item").t(image_path);
				}
				
				// Create <userfields>
				XMLBuilder elemUserfields = individual.e("userfields");
				Map<String, String> userfields = nd.getUserfields();
				for (String key : userfields.keySet()) {
					elemUserfields.e("field").a("key", key).t(userfields.get(key));
				}
			}
			
			result = builder.asString();
			
		} catch (Exception ex) {
			log.error("构建OWL字符串时出错", ex);
			throw new RuntimeException("构建OWL字符串时出错", ex);
		}
		return result;
	
	}
	
	private static String getParentUri(String id, SimpleJdbcTemplate sqlclient) {
		String uri = null;
		try {
			uri = sqlclient.queryForObject(
					"select uri_name as uriName from fishes where id=?", 
					java.lang.String.class, id);
		} catch (Exception ex) {
			uri = "";
			log.info(String.format("在试图获得父节点的uri时发现该父节点不存在[id=%1$s]，uri将保持为空。", id));
		}
		return uri;
	}
	
	private static String integerArray2String(int[] array) {
		StringBuilder sb = new StringBuilder();
		for(int i : array) 
			sb.append(String.valueOf(i));
		return sb.toString();
	}
	public Map<String, String> getUserfields() {
		return userfields;
	}
	public void setUserfields(Map<String, String> userfields) {
		this.userfields = userfields;
	}
	public int getDetailType() {
		return detailType;
	}
	public void setDetailType(int detailType) {
		this.detailType = detailType;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

}
