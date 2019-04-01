package com.yld.spring.ioc.springioc.interfaces.aop.advisor.impl;

import com.yld.spring.ioc.springioc.interfaces.aop.advisor.PointcutAdvisor;
import com.yld.spring.ioc.springioc.interfaces.aop.pointcut.Pointcut;
import com.yld.spring.ioc.springioc.interfaces.aop.pointcut.impl.AspectJExpressionPointcut;

/**
 * AspectJ切点通知者
 * @author yuleidian
 * @date 2019年3月22日
 */
public class AspectJPointcutAdvisor implements PointcutAdvisor {

	private String adviceBeanName;
	
	private String expression;
	
	private Pointcut aspectJPointcutExpressionPointcut;
	
	public AspectJPointcutAdvisor(String adviceBeanName, String expression) {
		super();
		this.adviceBeanName = adviceBeanName;
		this.expression = expression;
		this.aspectJPointcutExpressionPointcut = new AspectJExpressionPointcut(this.expression);
	}

	@Override
	public String getAdviceBeanName() {
		return this.adviceBeanName;
	}

	@Override
	public String getExpression() {
		return this.expression;
	}

	@Override
	public Pointcut getPointcut() {
		return this.aspectJPointcutExpressionPointcut;
	}

}
