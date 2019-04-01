package com.yld.spring.ioc.springioc.interfaces.aop.pointcut.impl;

import java.lang.reflect.Method;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import com.yld.spring.ioc.springioc.interfaces.aop.pointcut.Pointcut;

/**
 * AspectJ 切点
 * @author yuleidian
 * @date 2019年3月22日
 */
public class AspectJExpressionPointcut implements Pointcut {

	private static PointcutParser pp = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
	
	
	/** 表达式*/
	private String expression;
	
	private PointcutExpression pe;
	
	public AspectJExpressionPointcut(String expression) {
		super();
		this.expression = expression;
		this.pe = pp.parsePointcutExpression(expression);
	}

	@Override
	public boolean matchClass(Class<?> targetClass) {
		return pe.couldMatchJoinPointsInType(targetClass);
	}

	@Override
	public boolean matchMehtod(Method method, Class<?> targetClass) {
		ShadowMatch sm = pe.matchesMethodExecution(method);
		return sm.alwaysMatches();
	}

	public String getExpression() {
		return expression;
	}
}
