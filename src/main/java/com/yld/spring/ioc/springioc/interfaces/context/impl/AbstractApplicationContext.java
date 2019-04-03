package com.yld.spring.ioc.springioc.interfaces.context.impl;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactory;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanPostProcessor;
import com.yld.spring.ioc.springioc.interfaces.beans.impl.PreBuildBeanFactory;
import com.yld.spring.ioc.springioc.interfaces.context.ApplicationContext;

/**
 * 抽象应用上下文
 * @author yuleidian
 * @date 2019年4月3日
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
	
	protected BeanFactory beanFactory;
	
	public AbstractApplicationContext() {
		beanFactory = new PreBuildBeanFactory();
	}
	
	@Override
	public Object getBean(String name) throws Exception {
		return beanFactory.getBean(name);
	}

	@Override
	public void registerBeanPostProcessor(BeanPostProcessor bpp) {
		beanFactory.registerBeanPostProcessor(bpp);
	}

}
