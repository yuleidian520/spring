package com.yld.spring.ioc.springioc.interfaces.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.yld.spring.ioc.springioc.interfaces.aop.advisor.Advisor;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinition;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactory;
import com.yld.spring.ioc.springioc.interfaces.beans.impl.DefaultBeanFactory;
import com.yld.spring.ioc.springioc.utils.AopProxyUtils;

public class CglibDynamicAopProxy implements MethodInterceptor, AopProxy {

	private static Enhancer enhancer = new Enhancer();
	
	private String beanName;
	
	private Object target;
	
	private List<Advisor> matchAdvisors;
	
	private BeanFactory beanFactory;
	
	public CglibDynamicAopProxy(String beanName, Object target, List<Advisor> matchAdvisors, BeanFactory beanFactory) {
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
		Class<?> superClass = this.target.getClass();
		enhancer.setSuperclass(superClass);
		enhancer.setInterfaces(this.getClass().getInterfaces());
		enhancer.setCallback(this);
		Constructor<?> constructor = null;
		try {
			constructor = superClass.getConstructor(new Class<?>[] {});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (Objects.nonNull(constructor)) {
			return enhancer.create();
		} else {
			BeanDefinition bd = ((DefaultBeanFactory) beanFactory).getBeanDefinition(beanName);
			return enhancer.create(bd.getConstructor().getParameterTypes(), bd.getConstructorArgumentRealValues());
		}
	}


	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		return AopProxyUtils.applyAdvices(target, method, args, matchAdvisors, proxy, beanFactory);
	}

}
