package com.yld.spring.ioc.springioc.interfaces.impl;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.yld.spring.ioc.springioc.exceptions.BeanDefinitionRegisterException;
import com.yld.spring.ioc.springioc.interfaces.BeanDefinition;
import com.yld.spring.ioc.springioc.interfaces.BeanDefinitionRegistry;
import com.yld.spring.ioc.springioc.interfaces.BeanFactory;

/**
 * 默认bean工厂
 * @author yuleidian
 * @date 2019年3月20日
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

	private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<String, BeanDefinition>(255);
	
	private Map<String, Object> beans = new ConcurrentHashMap<String, Object>();
	
	@Override
	public Object getBean(String name) throws Exception {
		return doGetBean(name);
	}

	
	protected Object doGetBean(String name) throws Exception {
		Objects.requireNonNull(name, "beanName不能为空");
		
		Object instance = beans.get(name);
		if (Objects.nonNull(instance)) {
			return instance;
		}
		
		BeanDefinition bd = this.getBeanDefinition(name);
		Objects.requireNonNull(bd, "beanDefintion不能为空");
		
		Class<?> type = bd.getBeanClass();
		if (Objects.nonNull(type)) {
			if (StringUtils.isBlank(bd.getFactoryBeanName())) {
				instance = createInstanceByConstructor(bd);
			} else {
				instance = createInstanceByFactoryMethod(bd);
			}
		} else {
			instance = createInstanceByFactoryBean(bd);
		}
		
		this.doInit(bd, instance);
		
		if (bd.isSingleton()) {
			beans.put(name, instance);
		}
		return instance;
	}
	
	private void doInit(BeanDefinition bd, Object instance) throws Exception {
		if (StringUtils.isNotBlank(bd.getFactoryBeanName())) {
			instance.getClass().getMethod(bd.getInitMethodName(), null);
		}
	}


	private Object createInstanceByFactoryBean(BeanDefinition bd) throws Exception {
		Object factoryBean = this.doGetBean(bd.getFactoryBeanName());
		Method method = factoryBean.getClass().getMethod(bd.getFactoryBeanName(), null);
		return method.invoke(factoryBean, null);
	}


	private Object createInstanceByFactoryMethod(BeanDefinition bd) throws Exception {
		Class<?> clazz = bd.getBeanClass();
		Method method = clazz.getMethod(bd.getFactoryBeanName(), null);
		return method.invoke(clazz, null);
	}


	private Object createInstanceByConstructor(BeanDefinition bd) {
		return bd.getBeanClass().getInterfaces();
	}


	@Override
	public void registerBeanDefinition(String name, BeanDefinition beanDefintion) {
		Objects.requireNonNull(name, "beanName不能为空");
		Objects.requireNonNull(beanDefintion, "bean定义不能为空");
		
		if (!beanDefintion.validate()) {
			throw new BeanDefinitionRegisterException("beanName:[" + name + "的bean定义不合法:" + beanDefintion);
		}
		
		if (!this.containsBeanDefinition(name)) {
			throw new BeanDefinitionRegisterException("不能重复注册bean定义 beanName:" + name);
		}
		
		beanDefinitions.put(name, beanDefintion);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return beanDefinitions.get(beanName);
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return beanDefinitions.containsKey(beanName);
	}

}
