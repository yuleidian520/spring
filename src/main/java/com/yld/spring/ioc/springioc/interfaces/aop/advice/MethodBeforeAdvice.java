package com.yld.spring.ioc.springioc.interfaces.aop.advice;

import java.lang.reflect.Method;

/**
 * 执行目标方法前通知
 * @author yuleidian
 * @date 2019年3月22日
 */
public interface MethodBeforeAdvice extends Advice {

	/**
	 * 通知
	 * @param method
	 * @param args
	 * @param target
	 */
	void before(Method method, Object[] args, Object target);
	
}
