package com.yld.spring.ioc.springioc.interfaces.aop.advisor;

import com.yld.spring.ioc.springioc.interfaces.aop.pointcut.Pointcut;

/**
 * 切点通知者
 * @author yuleidian
 * @date 2019年3月22日
 */
public interface PointcutAdvisor extends Advisor {

	/**
	 * 获取切点
	 * @return
	 */
	Pointcut getPointcut();
	
}
