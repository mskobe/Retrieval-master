package com.zj.retrieval.master.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.zj.retrieval.master.Matrix;
import com.zj.retrieval.master.Node;
import com.zj.retrieval.master.User;
import com.zj.retrieval.master.dao.NodeDao;
import com.zj.retrieval.master.dao.UserDao;

public class CodeTest {
	
	private static Log log = LogFactory.getLog(CodeTest.class);
	
	@Test
	public void t1() throws Exception {
		DataSource ds = (DataSource) new FileSystemXmlApplicationContext("war/WEB-INF/beans.xml").getBean("dataSource");
		SimpleJdbcTemplate opt = new SimpleJdbcTemplate(ds);
		
		List<String> result = opt.query("select * from `string`", new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				System.out.println("Hello World");
				return null;
			}
			
		}, new HashMap());
		System.out.println(result.size());
	}
	
	@Test
	public void t2() throws Exception {
		List<String> array = new ArrayList<String>();
		String str = array.get(0);
		System.out.println(str == null ? "It's null" : "It's not null");
	}
	
	@Test
	public void t3() throws Exception {
		String selectValues = "012345".substring(1, "012345".length());
		System.out.println(selectValues);
	}
	
	@Test
	public void t4() {
		System.out.println(java.util.UUID.randomUUID().toString());
	}
	
	@Test
	public void t5() {
		System.out.println(UUID.randomUUID().toString());
	}
	
	@Test
	public void t6() {
		List<Integer> array = new ArrayList<Integer>();
		array.add(12);
		array.add(14);
		System.out.println(array.indexOf(14));
		
	}
	
	@Test
	public void dataBaseTest() {
		ApplicationContext spring =  new FileSystemXmlApplicationContext("/WebContent/WEB-INF/beans.xml");
		UserDao userDao = (UserDao) spring.getBean("userDao");
		List<User> queryResult = userDao.getAllUser();
		
		for (User user : queryResult) {
			System.out.println(user);
		}
	}
	
	@Test
	public void t7() {
		StringBuilder sb = new StringBuilder();
		sb.append("123");
		System.out.println(sb.substring(0, sb.length() - 1));
	}

}