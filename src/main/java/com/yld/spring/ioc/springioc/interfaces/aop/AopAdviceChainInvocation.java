package com.yld.spring.ioc.springioc.interfaces.aop;

import java.lang.reflect.Method;
import java.util.List;

import com.yld.spring.ioc.springioc.interfaces.aop.advice.AfterReturningAdvice;
import com.yld.spring.ioc.springioc.interfaces.aop.advice.MehtodInterceptorAdvice;
import com.yld.spring.ioc.springioc.interfaces.aop.advice.MethodBeforeAdvice;
// 3
public class AopAdviceChainInvocation {

	private static Method invokMethod;
	
	static {
		try {
			invokMethod = AopAdviceChainInvocation.class.getMethod("invoke");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private Object proxy;
	private Object target;
	private Method method;
	private Object[] args;
	private List<Object> advices;
	
	public AopAdviceChainInvocation(Object proxy, Object target, Method method, Object[] args, List<Object> advices) {
		super();
		this.proxy = proxy;
		this.target = target;
		this.method = method;
		this.args = args;
		this.advices = advices;
	}

	private int i = 0;
	
	public Object invoke() throws Exception {
		if (i < this.advices.size()) {
			Object advice = this.advices.get(i++);
			if (advices instanceof MethodBeforeAdvice) {
				((MethodBeforeAdvice)advice).before(invokMethod, null, this);
			} else if (advice instanceof MehtodInterceptorAdvice) {
				return ((MehtodInterceptorAdvice)advice).around(invokMethod, null, this);
			} else if (advices instanceof AfterReturningAdvice) {
				Object returnValue = this.invoke();
				((AfterReturningAdvice)advice).afterReturning(returnValue, method, args, advice);
				return returnValue;
			}
			return this.invoke();
		} else {
			return method.invoke(target, args);
		}
	}
}
