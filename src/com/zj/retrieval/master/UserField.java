package com.zj.retrieval.master;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserField {
	private static Log log = LogFactory.getLog(UserField.class);
	
	public static JSONArray parse(Map<String, String> fields) {
		JSONArray result = new JSONArray();
		for (String key : fields.keySet()) {
			JSONObject jField = new JSONObject();
			try {
				jField.put("key", key);
				jField.put("value", fields.get(key));
			} catch (JSONException e) { log.error("在将自定义字段转换成json格式时发生错误。", e); }
			result.put(jField);
		}
		return result;
	}
	
	public static Map<String, String> parse(JSONArray jUserfields) {
		try {
			Map<String, String> result = new HashMap<String, String>();
			for (int i = 0; i < jUserfields.length(); i++) {
				JSONObject jField = jUserfields.getJSONObject(i);
				result.put(jField.getString("key"), jField.getString("value"));
			}
			return result;
		} catch (JSONException e) { 
			log.error("在将json字符串解析成自定义字段时发生错误。", e);
			return null;
		}
	}
}
