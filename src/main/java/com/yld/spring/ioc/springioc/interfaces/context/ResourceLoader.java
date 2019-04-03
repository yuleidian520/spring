package com.yld.spring.ioc.springioc.interfaces.context;

import java.io.IOException;

/**
 * 
 * @author yuleidian
 * @date 2019年4月3日
 */
public interface ResourceLoader {

	/**
	 * 获取文件源
	 * @param location
	 * @return
	 */
	Resource getResource(String location) throws IOException;
	
}
