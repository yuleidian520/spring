package com.yld.spring.ioc.springioc.interfaces.beans;

/**
 * bean AOP处理器
 * @author yuleidian
 * @date 2019年4月1日
 */
public interface BeanPostProcessor {
	
	/**
	 * 执行目标bean初始化方法前
	 * @param bean
	 * @param beanName
	 * @return
	 */
	default Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
		return bean;
	} 
	
	/**
	 * 执行目标bean初始化方法后
	 * @param Bean
	 * @param beanName
	 * @return
	 */
	default Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
		return bean;
	}
	
}
