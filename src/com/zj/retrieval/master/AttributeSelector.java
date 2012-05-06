package com.zj.retrieval.master;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeSelector {
	private Map<Integer, Boolean> attributeMapping;
	private Map<Attribute, Boolean> newAttributeMapping;
	
	public void select(int index, boolean flag) throws AttributeSelectedWrongException {
		if (attributeMapping.containsKey(new Integer(index))) {
			attributeMapping.put(index, flag);
		} else {
			throw new AttributeSelectedWrongException("index参数错误，父类中没有该编号的特征。"); 
		}
	}
	
	public boolean hasAttribute(int index) {
		return attributeMapping.containsKey(index);
	}

	public AttributeSelector(List<Integer> attributeIds) {
		Map<Integer, Boolean> data = new HashMap<Integer, Boolean>();
		for (Integer id : attributeIds) {
			data.put(id, Boolean.FALSE);
		}
		this.attributeMapping = data;
		this.newAttributeMapping = new HashMap<Attribute, Boolean>();
	}
	
	public AttributeSelector() {
		this.attributeMapping = new HashMap<Integer, Boolean>();
		this.newAttributeMapping = new HashMap<Attribute, Boolean>();
	}

	public Map<Integer, Boolean> getAttributeMapping() {
		return attributeMapping;
	}

	public void setAttributeMapping(Map<Integer, Boolean> attributeMapping) {
		this.attributeMapping = attributeMapping;
		for (int key : this.attributeMapping.keySet()) {
			this.attributeMapping.put(key, false);
		}
	}

	public void addNewAttribute(Attribute newAttribute, boolean newAttributeFlag) {
		newAttributeMapping.put(newAttribute, Boolean.valueOf(newAttributeFlag));		
	}

	public Map<Attribute, Boolean> getNewAttributeMapping() {
		return newAttributeMapping;
	}

	public void setNewAttributeMapping(Map<Attribute, Boolean> newAttributeMapping) {
		this.newAttributeMapping = newAttributeMapping;
	}

	@Override
	public String toString() {
		return "AttributeSelector [attributeMapping=" + attributeMapping
				+ ", newAttributeMapping=" + newAttributeMapping + "]";
	}

}
