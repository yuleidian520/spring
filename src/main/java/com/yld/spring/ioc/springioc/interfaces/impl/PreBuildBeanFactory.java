package com.yld.spring.ioc.springioc.interfaces.impl;

import java.util.HashSet;
import java.util.Set;

import com.yld.spring.ioc.springioc.interfaces.BeanDefinition;

/**
 * 提前创建单例bean工厂
 * @author yuleidian
 * @date 2019年3月20日
 */
public class PreBuildBeanFactory extends DefaultBeanFactory {

	private Set<String> beanNames = new HashSet<String>();
	
	@Override
	public void registerBeanDefinition(String name, BeanDefinition beanDefintion) {
		super.registerBeanDefinition(name, beanDefintion);
		synchronized (beanNames) {
			beanNames.add(name);
		}
	}
	
	public void preInstantiateSingletions() throws Exception {
		synchronized (beanNames) {
			beanNames.stream().forEach(beanName-> {
				BeanDefinition bd = this.getBeanDefinition(beanName);
				if (bd.isSingleton()) {
					try {
						this.doGetBean(beanName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
}
