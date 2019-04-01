package com.yld.spring.ioc.springioc.interfaces.aop.advice;

import java.lang.reflect.Method;

/**
 * 执行目标方法后通知
 * @author yuleidian
 * @date 2019年3月22日
 */
public interface AfterReturningAdvice extends Advice {

	/**
	 * 通知
	 * @param retrunValue
	 * @param method
	 * @param args
	 * @param target
	 */
	void afterReturning(Object returnValue, Method method, Object[] args, Object target);
	
}
