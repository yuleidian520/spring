package com.yld.spring.ioc.springioc.interfaces.context.impl;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinitionRegistry;
import com.yld.spring.ioc.springioc.interfaces.context.Resource;

/**
 * 注解应用上下文
 * @author yuleidian
 * @date 2019年4月3日
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

	private ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner;
	
	public AnnotationApplicationContext(String...basePackages) {
		super();
		classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry)this.beanFactory);
		classPathBeanDefinitionScanner.scan(basePackages);
	}

	@Override
	public Resource getResource(String location) {
		return null;
	}

}
