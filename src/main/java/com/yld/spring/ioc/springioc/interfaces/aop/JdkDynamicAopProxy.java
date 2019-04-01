package com.yld.spring.ioc.springioc.interfaces.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import com.yld.spring.ioc.springioc.interfaces.aop.advisor.Advisor;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactory;
import com.yld.spring.ioc.springioc.utils.AopProxyUtils;

public class JdkDynamicAopProxy implements InvocationHandler , AopProxy {

	private String beanName;
	
	private Object target;
	
	private List<Advisor> matchAdvisors;
	
	private BeanFactory beanFactory;
	
	
	public JdkDynamicAopProxy(String beanName, Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) {
		super();
		this.beanName = beanName;
		this.target = target;
		this.matchAdvisors = matchAdvisors;
		this.beanFactory = beanFactory;
	}

	@Override
	public Object getProxy() {
		return this.getProxy(target.getClass().getClassLoader());
	}

	@Override
	public Object getProxy(ClassLoader classLoader) {
		return Proxy.newProxyInstance(classLoader, target.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return AopProxyUtils.applyAdvices(target, method, args, matchAdvisors, proxy, beanFactory);
	}

}
