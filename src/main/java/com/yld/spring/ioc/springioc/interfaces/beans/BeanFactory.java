package com.yld.spring.ioc.springioc.interfaces.beans;

/**
 * bean工厂
 * @author yuleidian
 * @date 2019年3月20日
 */
public interface BeanFactory {

	/**
	 * 获取bean
	 * @param name
	 * @return
	 */
	Object getBean(String name) throws Exception;
	
	/**
	 * 注册AOPbean
	 * @param bpp
	 */
	void registerBeanPostProcessor(BeanPostProcessor bpp);
	
}
