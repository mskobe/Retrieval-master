package com.zj.retrieval.master;

import java.util.HashMap;
import java.util.Map;

public class Attribute {

	public static final int YES    = 2;
	public static final int NO     = 1;
	public static final int UNKNOW = 3;
	
	private String desc = "";
	private String name = "";
	private String enName = "";
	private String image = "";
	private Map<String, String> userfields = new HashMap<String, String>();
	
	public Attribute() {}

	public Attribute(String name) {
		this(name, "no english name", "no description.");
	}
	
	public Attribute(String name, String enName, String desc) {
		this(name, enName, desc, null);
	}
	
	public Attribute(String name, String enName, String desc, String image) {
		this.desc = desc;
		this.name = name;
		this.enName = enName;
		this.image = image;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnglishName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + name + "\n");
		sb.append("EnglishName: " + enName + "\n");
		sb.append("Descr: " + desc + "\n");
		sb.append("Image: " + image);
		for(String key : userfields.keySet()) {
			sb.append(key).append(": ").append(userfields.get(key)).append("\n");
		}
		return sb.toString();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Map<String, String> getUserFields() {
		return userfields;
	}

	public void setUserFields(Map<String, String> userfields) {
		this.userfields = userfields;
	}
}
