package com.yld.spring.ioc.springioc.interfaces.context;

import java.io.IOException;
import java.io.InputStream;

/**
 * 输入流源
 * @author yuleidian
 * @date 2019年4月3日
 */
public interface InputStreamSource {

	/**
	 * 获取输入流
	 * @return
	 */
	InputStream getInputStream() throws IOException;
	
}
