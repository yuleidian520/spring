package com.yld.spring.ioc.springioc.interfaces.aop;

import java.util.List;

import com.yld.spring.ioc.springioc.interfaces.aop.advisor.Advisor;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactory;

/**
 * AOP代理工厂
 * @author yuleidian
 * @date 2019年4月1日
 */
public interface AopProxyFactory {

	
	AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Exception;
	
	static AopProxyFactory getDefaultAopProxyFactory() {
		return new DefaultAopProxyFactory();
	}
}
