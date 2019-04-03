package com.yld.spring.ioc.springioc.interfaces.context;

import java.io.File;

/**
 * 解析文件定义源
 * @author yuleidian
 * @date 2019年4月3日
 */
public interface Resource extends InputStreamSource {

	/** 类路径*/
	String CLASS_PATH_PREFIX = "classpath:";

	/** 文件路径*/
	String FILE_SYSTEM_PREFIX = "file:";
	
	/**
	 * 判断文件源是否存在
	 * @return
	 */
	boolean exists();
	
	/**
	 * 判断是否可读
	 * @return
	 */
	boolean isReadable();
	
	/**
	 * 是否打开
	 * @return
	 */
	boolean isOpen();
	
	/**
	 * 获取文件
	 * @return
	 */
	File getFile();
}
