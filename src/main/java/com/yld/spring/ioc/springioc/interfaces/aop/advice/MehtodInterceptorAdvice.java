package com.yld.spring.ioc.springioc.interfaces.aop.advice;

import java.lang.reflect.Method;

/**
 * 环绕通知
 * @author yuleidian
 * @date 2019年3月22日
 */
public interface MehtodInterceptorAdvice extends Advice {

	/**
	 * 通知
	 * @param method
	 * @param args
	 * @param target
	 * @return
	 */
	Object around(Method method, Object[] args, Object target);
	
}
