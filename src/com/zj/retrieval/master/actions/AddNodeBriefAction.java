package com.zj.retrieval.master.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.zj.retrieval.master.Attribute;
import com.zj.retrieval.master.AttributeSelectedWrongException;
import com.zj.retrieval.master.AttributeSelector;
import com.zj.retrieval.master.DetailType;
import com.zj.retrieval.master.Node;
import com.zj.retrieval.master.NodeType;
import com.zj.retrieval.master.UserField;
import com.zj.retrieval.master.Util;
import com.zj.retrieval.master.dao.NodeDao;
import com.zj.retrieval.master.dao.UserDao;

public class AddNodeBriefAction {
	private static Log log = LogFactory.getLog(AddNodeBriefAction.class);
	private String node_name_en;
	private String node_name;
	private String parent_id;
	private String parent_attr;
	private String new_attr;
	private boolean isError;
	
	public boolean getIsError() {
		return this.isError;
	}
	
	private String message;
	private String contact;
	private String node_id;
	
	private String post_user_name;
	private String post_user_password;
	
	public String execute() {
		try {
			UserDao userDao = Util.getUserDao();
			if (!userDao.verifyUser(post_user_name, post_user_password)) {
				this.isError = true;
				this.message = "�û������������.";
				return ActionSupport.ERROR;
			}
			
			Node new_node = new Node();
			new_node.setId(node_id);
			new_node.setEnglishName(node_name_en);
			new_node.setName(node_name);
			new_node.setNodeType(NodeType.NODETYPE_INDIVIDUAL);
			new_node.setParentId(parent_id);
			new_node.setContact(contact);
			new_node.setDetailType(DetailType.BRIEF);
			
			NodeDao ndService =  Util.getNodeDao();;
			
			Node parent_node = ndService.getNodeById(new_node.getParentId());
			log.info("�ҵ����ڵ㣺" + parent_node);
			AttributeSelector attrSelector = ndService.getAttributeSelector(parent_node);
			String[] selectedAttributes = parent_attr.equals("") ?
				new String[0] : parent_attr.split(" ");
			for (int i = 0; i < selectedAttributes.length; i++) {
				int selectedAttribute = Integer.valueOf(selectedAttributes[i]);
				attrSelector.select(selectedAttribute, true);
				log.info(String.format("ѡ�񸸽ڵ�����[id=%1$s, name=%2$s]", selectedAttribute, 
						parent_node.getRetrievalDataSource().getAttributes().get(selectedAttribute).getName()));
			}
			JSONArray jNewAttributes = new JSONArray(new_attr);
			for (int i = 0; i < jNewAttributes.length(); i++) {
				JSONObject jAttr = jNewAttributes.getJSONObject(i);
				Attribute newAttr = new Attribute(jAttr.getString("new_attr_name"),
						                          jAttr.getString("new_attr_name_en"),
						                          jAttr.getString("new_attr_desc"),
						                          jAttr.getString("new_attr_image"));
				JSONArray jAttrUserfields = jAttr.getJSONArray("new_attr_user_field");
				newAttr.setUserFields(UserField.parse(jAttrUserfields));
				log.info("����ӵ����ԣ�" + newAttr);
				attrSelector.addNewAttribute(newAttr, true);
			}
			ndService.addNodeBrief(new_node, parent_node, attrSelector);
//			ndService.addNode(new_node, parent_node, attrSelector);
			
			this.message = "Success, o(��_��)o...";
			return ActionSupport.SUCCESS;
			
		} catch (JSONException e) {
			log.error("���ݸ�ʽ����", e);
			this.message = "�ͻ��˳��������ݸ�ʽ������ʹ�����µĿͻ��˳���";
			return ActionSupport.ERROR;
		} catch (AttributeSelectedWrongException e) {
			log.info("�����������ĸ��ڵ�");
			this.message = "�����������ĸ��ڵ����ԣ�";
			return ActionSupport.ERROR;
		} catch (NumberFormatException ex) {
			log.info("�ͻ��������˴���ĸ��ڵ�����");
			this.message = "���ڵ����Ը�ʽ����";
			return ActionSupport.ERROR;
		} catch (Exception ex) {
			log.error("������ӽ��ʱ����δ֪����", ex);
			this.message = "������ӽ��ʱ����δ֪����";
			return ActionSupport.ERROR;
		}
	
	}

	public void setNode_name_en(String name_en) {
		this.node_name_en = name_en;
	}

	public void setNode_name(String name) {
		this.node_name = name;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public void setParent_attr(String parent_attr) {
		this.parent_attr = parent_attr;
	}

	public void setNew_attr(String new_attr) {
		this.new_attr = new_attr;
	}

	public String getMessage() {
		return message;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public void setPost_user_name(String post_user_name) {
		this.post_user_name = post_user_name;
	}

	public void setPost_user_password(String post_user_password) {
		this.post_user_password = post_user_password;
	}
}
