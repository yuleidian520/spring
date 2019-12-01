package com.yld.spring.ioc.springioc.interfaces.beans.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.yld.spring.ioc.springioc.exceptions.BeanDefinitionRegisterException;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinition;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinitionRegistry;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactory;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanFactoryAware;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanPostProcessor;
import com.yld.spring.ioc.springioc.utils.BeanUtils;

/**
 * 默认bean工厂
 * @author yuleidian
 * @date 2019年3月20日
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {

	private Map<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<String, BeanDefinition>(255);
	
	private Map<String, Object> beans = new ConcurrentHashMap<String, Object>();
	
	private ThreadLocal<Set<String>> buildingBeans = new ThreadLocal<>();
	
	// private Set<String> aliasList = new HashSet<String>(255);
	
	private List<BeanPostProcessor> beanPostProcessors = Collections.synchronizedList(new ArrayList<>());
	
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
		
		Set<String> ingBean = this.buildingBeans.get();
		if (CollectionUtils.isEmpty(ingBean)) {
			ingBean = new HashSet<String>();
			this.buildingBeans.set(ingBean);
		}
		
		if (ingBean.contains(name)) {
			throw new Exception(name + "循环依赖");
		}
		
		ingBean.add(name);
		
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
		
		ingBean.remove(name);
		
		this.setPropertyDIValue(bd, instance);
		
		instance = this.applyPostProcessBeforeInitialization(instance, name);
		
		this.doInit(bd, instance);
		
		instance = this.applyPostProcessAfterInitialization(instance, name);
		
		if (bd.isSingleton()) {
			beans.put(name, instance);
		}
		return instance;
	}
	
	private Object applyPostProcessBeforeInitialization(Object instance, String name) throws Exception {
		if (!CollectionUtils.isEmpty(beanPostProcessors)) {
			for (BeanPostProcessor bpp : this.beanPostProcessors) {
				instance = bpp.postProcessBeforeInitialization(instance, name);
			}
		}
		return instance;
	}
	
	private Object applyPostProcessAfterInitialization(Object instance, String name) throws Exception {
		if (!CollectionUtils.isEmpty(beanPostProcessors)) {
			for (BeanPostProcessor bpp : this.beanPostProcessors) {
				instance = bpp.postProcessAfterInitialization(instance, name);
			}
		}
		return instance;
	}


	private void setPropertyDIValue(BeanDefinition bd, Object instance) throws Exception {
		if (CollectionUtils.isEmpty(bd.getPropertyValues())) {
			return;
		}
		Object v = null;
		for (PropertyValue pv : bd.getPropertyValues()) {
			if (StringUtils.isBlank(pv.getName())) {
				continue;
			}
			
			Class<?> clazz = instance.getClass();
			Field f = clazz.getDeclaredField(pv.getName());
			
			f.setAccessible(true);
			
			v = BeanUtils.getRealValue(this, pv.getValue());
			f.set(instance, v);
		}
	}


	private void doInit(BeanDefinition bd, Object instance) throws Exception {
		if (StringUtils.isNotBlank(bd.getFactoryBeanName())) {
			instance.getClass().getMethod(bd.getInitMethodName());
		}
	}


	private Object createInstanceByFactoryBean(BeanDefinition bd) throws Exception {
		Object factoryBean = this.doGetBean(bd.getFactoryBeanName());
		Object[] args = this.getConstructorArumentValues(bd);
		Method method = this.determineFactoryMethod(bd, args, factoryBean.getClass());
		return method.invoke(factoryBean, args);
	}

	private Method determineFactoryMethod(BeanDefinition bd, Object[] args, Class<?> type) throws Exception {
		if (Objects.isNull(type)) {
			type = bd.getBeanClass();
		}
		
		String methodName = bd.getFactoryMethodName();
		
		if (Objects.isNull(args)) {
			return type.getMethod(methodName);
		}
		
		Method result = bd.getFactoryMethod();
		if (Objects.nonNull(result)) {
			return result;
		}
		
		Class<?>[] paramTypes = new Class[args.length];
		int j = 0;
		for (Object p : args) {
			paramTypes[j++] = p.getClass();
		}
		
		result = type.getMethod(methodName, paramTypes);
		if (Objects.isNull(result)) {
			outer : for (Method tempM : type.getMethods()) {
				if (tempM.getName().equals(methodName)) {
					continue;
				}
				
				Class<?>[] paramterTypes = result.getParameterTypes();
				if (paramterTypes.length == args.length) {
					for (int i = 0; i < paramterTypes.length; i++) {
						if (!paramterTypes[i].isAssignableFrom(args[i].getClass())) {
							continue outer;
						}
					}
					
					result = tempM;
					break outer;
				}
			}
		}
		
		if (Objects.nonNull(result)) {
			if (bd.isPrototype()) {
				bd.setFactoryMethod(result);
			}
		} else {
			throw new Exception("无法找工厂方法[" + methodName + "]");
		}
		return result;
	}


	private Object createInstanceByFactoryMethod(BeanDefinition bd) throws Exception {
		Class<?> clazz = bd.getBeanClass();
		Method method = clazz.getMethod(bd.getFactoryBeanName());
		return method.invoke(clazz);
	}


	private Object createInstanceByConstructor(BeanDefinition bd) {
		try {
			Object[] args = this.getConstructorArumentValues(bd);
			if (Objects.nonNull(args)) {
				return this.determineContructor(bd, args).newInstance(args);
			}
			return bd.getBeanClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Constructor<?> determineContructor(BeanDefinition bd, Object...args) throws Exception {
		if (Objects.isNull(args)) {
			return bd.getBeanClass().getConstructor();
		}
		
		Constructor<?> ct = null;
		
		ct = bd.getConstructor();
		if (Objects.nonNull(ct)) {
			return ct;
		}
		
		Class<?>[] paramTypes = new Class[args.length];
		int j = 0;
		for (Object p : args) {
			paramTypes[j++] = p.getClass();
		}
		
		ct = bd.getClass().getConstructor(paramTypes);
		
		if (Objects.isNull(ct)) {
			outer : for (Constructor<?> tempCt : bd.getClass().getConstructors()) {
				Class<?>[] paramterTypes = tempCt.getParameterTypes();
				if (paramterTypes.length == args.length) {
					for (int i = 0; i < paramterTypes.length; i++) {
						if (paramterTypes[i].isAssignableFrom(args[i].getClass())) {
							continue outer;
						}
					}
					
					ct = tempCt;
					break outer;
				}
			}
		}
		
		if (Objects.nonNull(ct)) {
			if (bd.isPrototype()) {
				bd.setConstructor(ct);
			}
		} else {
			throw new Exception("无法找到构造器");
		}
		return ct; 
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

	private Object[] getConstructorArumentValues(BeanDefinition bd) throws Exception {
		return getRealValues(bd.getConstructorArgumentValues());
	}
	
	private Object[] getRealValues(List<?> defs) throws Exception {
		if (CollectionUtils.isEmpty(defs)) {
			return null;
		}
		
		Object[] result = new Object[defs.size()];
		int i = 0;
		for (Object rv : defs) {
			result[i++] = BeanUtils.getRealValue(this, rv);;
		}
		return result;
	}
	
	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return beanDefinitions.get(beanName);
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return beanDefinitions.containsKey(beanName);
	}


	@Override
	public void registerBeanPostProcessor(BeanPostProcessor bpp) {
		beanPostProcessors.add(bpp);
		if (bpp instanceof BeanFactoryAware) {
			((BeanFactoryAware)bpp).setBeanFactory(this);
		}
	}

}
