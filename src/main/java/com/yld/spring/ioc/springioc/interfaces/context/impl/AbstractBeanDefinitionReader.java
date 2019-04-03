package com.yld.spring.ioc.springioc.interfaces.context.impl;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinitionRegistry;
import com.yld.spring.ioc.springioc.interfaces.context.BeanDefinitionReader;

/**
 * 抽象bean定义读取器
 * @author yuleidian
 * @date 2019年4月3日
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

	protected BeanDefinitionRegistry beanDefinitionRegistry;
	
	public AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}
	
}
