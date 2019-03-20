package com.yld.spring.ioc.springioc.interfaces;

/**
 * bean定义注册
 * @author yuleidian
 * @date 2019年3月20日
 */
public interface BeanDefinitionRegistry {

	/**
	 * 注册bean定义
	 * @param name
	 * @param beanDefintion
	 */
	void registerBeanDefinition(String name, BeanDefinition beanDefintion);
	
	/**
	 * 获取bean定义
	 * @param beanDefintion
	 * @return
	 */
	BeanDefinition getBeanDefinition(String beanName);
	
	/**
	 * 验证bean是否存在
	 * @param beanName
	 * @return
	 */
	boolean containsBeanDefinition(String beanName);
}
