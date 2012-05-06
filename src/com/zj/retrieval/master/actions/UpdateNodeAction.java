package com.zj.retrieval.master.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
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

public class UpdateNodeAction {
	private static Log log = LogFactory.getLog(AddNodeAction.class);
	private String desc;
	private String node_name_en;
	private String node_name;
	private String parent_id;
	private String uri;
	private String images;
	private String user_field;
	private String parent_attr;
	private String new_attr;
	private String node_id;
	//private int detailType;
	
	private String message;
	private boolean isError;
	
	public boolean getIsError() {
		return this.isError;
	}
	
	private String post_user_name;
	private String post_user_password;
	
	public String execute() {
		try {
			UserDao userDao = Util.getUserDao();
			if (!userDao.verifyUser(post_user_name, post_user_password)) {
				this.isError = true;
				this.message = "用户名或密码错误.";
				return ActionSupport.ERROR;
			}
			
			HttpServletRequest request = ServletActionContext.getRequest();
			String detail = request.getParameter("data_integrity");
			Integer intDetail = new Integer(Integer.parseInt(detail));
			int detailType = intDetail.intValue();
			
			
			NodeDao nodeDao =  Util.getNodeDao();
			Node nd = nodeDao.getNodeById(node_id);
			nd.setId(node_id);
			nd.setDesc(desc);
			nd.setEnglishName(node_name_en);
			nd.setName(node_name);
			nd.setNodeType(NodeType.NODETYPE_CLASS); // 先暂时写死
			nd.setUri(uri);
			nd.setUriName(nd.getUri() + "#" + nd.getEnglishName());
			nd.setDetailType(detailType);
			//nd.setDetailType(DetailType.FULL);
			
			// 解析images
			List<String> fullPaths = new ArrayList<String>();
			for (String image_id : images.split(";")) {
				fullPaths.add("images/" + image_id);
			}
			nd.setImages(fullPaths);
			
			// 解析自定义字段
			if (user_field != null && !user_field.isEmpty()) {
				JSONArray userFieldJSONArray = new JSONArray(user_field);
				nd.setUserfields(UserField.parse(userFieldJSONArray));
			}
			
			
			nodeDao.updateNode(nd);
			
//			Node parentNode = nodeDao.getNodeById(newNode.getParentId());
//			log.info("找到父节点：" + parentNode);
//			AttributeSelector attrSelector = nodeDao.getAttributeSelector(parentNode);
//			String[] selectedAttributes = parent_attr.isEmpty() ?
//				new String[0] : parent_attr.split(" ");
//			for (int i = 0; i < selectedAttributes.length; i++) {
//				int selectedAttribute = Integer.valueOf(selectedAttributes[i]);
//				attrSelector.select(selectedAttribute, true);
//				log.info(String.format("选择父节点属性[id=%1$s, name=%2$s]", selectedAttribute, 
//						parentNode.getRetrievalDataSource().getAttributes().get(selectedAttribute).getName()));
//			}
//			JSONArray jNewAttributes = new JSONArray(new_attr);
//			for (int i = 0; i < jNewAttributes.length(); i++) {
//				JSONObject jAttr = jNewAttributes.getJSONObject(i);
//				Attribute newAttr = new Attribute(jAttr.getString("new_attr_name"),
//						                          jAttr.getString("new_attr_name_en"),
//						                          jAttr.getString("new_attr_desc"),
//						                          jAttr.getString("new_attr_image"));
//				JSONArray jAttrUserfields = jAttr.getJSONArray("new_attr_user_field");
//				newAttr.setUserFields(UserField.parse(jAttrUserfields));
//				log.info("新添加的属性：" + newAttr);
//				attrSelector.addNewAttribute(newAttr, true);
//			}
//			nodeDao.addNode(newNode, parentNode, attrSelector);
			
			this.message = "Success, o(∩_∩)o...";
			return ActionSupport.SUCCESS;
			
		} catch (JSONException e) {
			this.message = "客户端程序发送数据格式错误！请使用最新的客户端程序。";
			return ActionSupport.ERROR;
		} catch (NumberFormatException ex) {
			this.message = "父节点属性格式错误！";
			return ActionSupport.ERROR;
		} catch (Exception ex) {
			this.message = ex.getMessage();
			return ActionSupport.ERROR;
		}
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public void setUser_field(String user_field) {
		this.user_field = user_field;
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

	public void setPost_user_name(String post_user_name) {
		this.post_user_name = post_user_name;
	}

	public void setPost_user_password(String post_user_password) {
		this.post_user_password = post_user_password;
	}
	
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
}
