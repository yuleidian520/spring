package com.yld.spring.ioc.springioc.interfaces.aop;

/**
 * Aop代理
 * @author yuleidian
 * @date 2019年4月1日
 */
public interface AopProxy {

	/**
	 * 获取代理
	 * @return
	 */
	Object getProxy();
	
	/**
	 * 获取代理
	 * @param classLoader
	 * @return
	 */
	Object getProxy(ClassLoader classLoader);
	
}
