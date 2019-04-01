package com.yld.spring.ioc.springioc.interfaces.aop.advisor;

/**
 * 通知者
 * @author yuleidian
 * @date 2019年3月22日
 */
public interface Advisor {

	/**
	 * 获取beanName
	 * @return
	 */
	String getAdviceBeanName();
	
	/**
	 * 获取切点表达式
	 * @return
	 */
	String getExpression();
	
}
