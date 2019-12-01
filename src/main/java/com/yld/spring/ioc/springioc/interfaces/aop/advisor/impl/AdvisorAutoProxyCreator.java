package com.yld.spring.ioc.springioc.interfaces.aop.advisor.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.yld.spring.ioc.springioc.interfaces.aop.AopProxyFactory;
import com.yld.spring.ioc.springioc.interfaces.aop.advisor.Advisor;
import com.yld.spring.ioc.springioc.interfaces.aop.advisor.AdvisorRegistry;
import com.yld.spring.ioc.springioc.interfaces.aop.advisor.PointcutAdvisor;
import com.yld.spring.ioc.springioc.interfaces.aop.pointcut.Pointcut;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactory;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactoryAware;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanPostProcessor;

/**
 * 自动创建通知者bean
 * @author yuleidian
 * @date 2019年4月1日
 */
public class AdvisorAutoProxyCreator implements AdvisorRegistry, BeanPostProcessor, BeanFactoryAware {

	private List<Advisor> advisors;
	
	private BeanFactory beanFactory;
	
	public AdvisorAutoProxyCreator() {
		this.advisors = new ArrayList<>();
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void registAdvisor(Advisor ad) {
		this.advisors.add(ad);
	}

	@Override
	public List<Advisor> getAdvisors() {
		return this.advisors;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
		List<Advisor> matchAdvisors = getMatchedAdvisors(bean, beanName);
		
		if (!CollectionUtils.isEmpty(matchAdvisors)) {
			bean = this.createProxy(bean, beanName, matchAdvisors);
		}
		
		return bean;
	}

	private List<Advisor> getMatchedAdvisors(Object bean, String beanName) {
		if (CollectionUtils.isEmpty(advisors)) {
			return null;
		}
		
		Class<?> beanClass = bean.getClass();
		List<Method> allMethods = getBeanClassAllMethods(beanClass);
		List<Advisor> matchAdvisors = new ArrayList<>();
		for (Advisor ad : this.advisors) {
			if (ad instanceof PointcutAdvisor) {
				if (isPointcutMatchBean((PointcutAdvisor)ad , beanClass, allMethods)) {
					matchAdvisors.add(ad);
				}
			}
		}
		
		return matchAdvisors;
	}
	
	private boolean isPointcutMatchBean(PointcutAdvisor pa, Class<?> beanClass, List<Method> allMethods) {
		Pointcut p = pa.getPointcut();
		if (!p.matchClass(beanClass)) {
			return false;
		}
		
		for (Method method: allMethods) {
			if (p.matchMehtod(method, beanClass)) {
				return true;
			}
		}
		return false;
	}

	private List<Method> getBeanClassAllMethods(Class<?> beanClass) {
		List<Method> allMethod = new LinkedList<>();
		Set<Class<?>> classes = new LinkedHashSet<>(ClassUtils.getAllInterfacesForClassAsSet(beanClass));
		classes.add(beanClass);
		for (Class<?> clazz : classes) {
			Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
			for (Method m : methods) {
				allMethod.add(m);
			}
		} 
		return allMethod;
	}

	private Object createProxy(Object bean, String beanName, List<Advisor> matchAdvisors) throws Exception {
		return AopProxyFactory.getDefaultAopProxyFactory().createAopProxy(bean, beanName, matchAdvisors, beanFactory).getProxy();
	}

}
