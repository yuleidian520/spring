package com.yld.spring.ioc.springioc.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.yld.spring.ioc.springioc.interfaces.aop.AopAdviceChainInvocation;
import com.yld.spring.ioc.springioc.interfaces.aop.advisor.Advisor;
import com.yld.spring.ioc.springioc.interfaces.aop.advisor.PointcutAdvisor;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactory;

public abstract class AopProxyUtils {

	public static Object applyAdvices(Object target, Method method, Object[] args, List<Advisor> matchAdvisors, Object proxy, BeanFactory beanFactory) throws Exception {
		List<Object> advices = getShouldApplyAdvices(target.getClass(), method, matchAdvisors, beanFactory);
		if (CollectionUtils.isEmpty(advices)) {
			return method.invoke(target, args);
		} else {
			AopAdviceChainInvocation chain = new AopAdviceChainInvocation(proxy, target, method, args, advices);
			return chain.invoke();
		}
	}
	
	public static List<Object> getShouldApplyAdvices(Class<?> beanClass, Method method, List<Advisor> matchAdvisors, BeanFactory beanFactory) throws Exception {
		if (CollectionUtils.isEmpty(matchAdvisors)) {
			return null;
		}
		List<Object> advices = new ArrayList<>();
		for (Advisor ad : matchAdvisors) {
			if (ad instanceof PointcutAdvisor) {
				if (((PointcutAdvisor)ad).getPointcut().matchMehtod(method, beanClass)) {
					advices.add(beanFactory.getBean(ad.getAdviceBeanName()));
				}
				
			}
		}
		return advices;
	}
}
