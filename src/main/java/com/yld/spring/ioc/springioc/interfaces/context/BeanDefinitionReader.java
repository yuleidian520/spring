package com.yld.spring.ioc.springioc.interfaces.context;

/**
 * bean定义读取器
 * @author yuleidian
 * @date 2019年4月3日
 */
public interface BeanDefinitionReader {

	/**
	 * 加载文件源
	 * @param resource
	 */
	void loadBeanDefinitions(Resource resource);
	
	void loadBeanDefinitions(Resource...resources);
}
