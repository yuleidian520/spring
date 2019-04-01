package com.yld.spring.ioc.springioc.interfaces.aop.advisor;

import java.util.List;

/**
 * 注册通知者
 * @author yuleidian
 * @date 2019年4月1日
 */
public interface AdvisorRegistry {

	/**
	 * 注册观察者
	 * @param ad
	 */
	void registAdvisor(Advisor ad);
	
	/**
	 * 获取观察者
	 * @return
	 */
	List<Advisor> getAdvisors();
}
