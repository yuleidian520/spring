package com.yld.spring.ioc.springioc.interfaces.aop;

import java.util.List;

import com.yld.spring.ioc.springioc.interfaces.aop.advisor.Advisor;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactory;

public class DefaultAopProxyFactory implements AopProxyFactory {

	@Override
	public AopProxy createAopProxy(Object bean, String beanName, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Exception {
		if (shouldUseJDKDynamicProxy(bean, beanName)) {
			return new JdkDynamicAopProxy(beanName, bean, matchAdvisors, beanFactory);
		} else {
			return new CglibDynamicAopProxy(beanName, bean, matchAdvisors, beanFactory);
		}
	}

	
	private boolean shouldUseJDKDynamicProxy(Object bean, String beanName) {
		return false;
	}
	
	
}
