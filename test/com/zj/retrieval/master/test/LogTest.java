package com.zj.retrieval.master.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.junit.Test;

public class LogTest {
	private static Log log = LogFactory.getLog(LogTest.class);
	
	@Test
	public void logTest1() {
		log.debug("中文");
		log.info("ddfdf");
//		log.error("cuowu", new RuntimeException("dfdfdf"));
	}
}
