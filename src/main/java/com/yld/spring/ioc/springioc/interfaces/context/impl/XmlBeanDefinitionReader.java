package com.yld.spring.ioc.springioc.interfaces.context.impl;

import java.util.Objects;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinitionRegistry;
import com.yld.spring.ioc.springioc.interfaces.context.Resource;

/**
 * xmlBean定义读取器
 * @author yuleidian
 * @date 2019年4月3日
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
	
	public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
		super(beanDefinitionRegistry);
	}

	@Override
	public void loadBeanDefinitions(Resource resource) {
		this.loadBeanDefinitions(new Resource[]{resource});
	}

	@Override
	public void loadBeanDefinitions(Resource... resources) {
		if (Objects.nonNull(resources) && resources.length > 0) {
			for (Resource r : resources) {
				parseXml(r);
			}
		}
	}

	/**
	 * 解析xml
	 * @param resource
	 */
	private void parseXml(Resource resource) {
		
	}
	
}
