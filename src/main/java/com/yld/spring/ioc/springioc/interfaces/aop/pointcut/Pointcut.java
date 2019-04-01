package com.yld.spring.ioc.springioc.interfaces.aop.pointcut;

import java.lang.reflect.Method;

/**
 * 切点
 * @author yuleidian
 * @date 2019年3月22日
 */
public interface Pointcut {

	boolean matchClass(Class<?> targetClass);
	
	boolean matchMehtod(Method method, Class<?> targetClass);
}
