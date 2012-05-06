//package com.zj.retrieval.master.test;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.junit.Test;
//
//public class FileIOTest {
//	private static Log log = LogFactory.getLog(FileIOTest.class);
//	
//	@Test
//	public void CreateFolderTest() throws IOException {
//		
//		File newfile = new File("E:\\toPhone");
//		if (!newfile.exists()) {
//			log.info("文件不存在，现在�?��创建");
//			log.info(newfile.mkdirs() ? "已创建目录以及所有必�?��父目�? : "目录结构已存�?);
////			log.info(newfile.createNewFile() ? "指定的文件不存在并成功地创建" : "指定的文件已经存�?);
//		} else {
//			log.info("exists!");
//			log.info(newfile.isDirectory());
//		}
//	}
//}
