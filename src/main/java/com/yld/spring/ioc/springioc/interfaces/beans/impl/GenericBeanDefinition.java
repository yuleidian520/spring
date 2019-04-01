package com.yld.spring.ioc.springioc.interfaces.beans.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinition;

/**
 * 通用bean定义
 * @author yuleidian
 * @date 2019年3月20日
 */
public class GenericBeanDefinition implements BeanDefinition {

	private Class<?> beanClass;
	
	private String scope = SCOPE_SIGLETION;
	
	private String factoryBeanName;
	
	private String factoryMethodName;
	
	private String initMethod;
	
	private String destoryMethodName;
	
	private List<Object> constructorArgumentValues;
	
	private Constructor<?> constructor;
	
	private Method factoryMethod;
	
	private List<PropertyValue> propertyValues;
	
	private String  aliasName;
	
	private ThreadLocal<Object[]> constructorArgumentRealValues;
	
	
	@Override
	public boolean isSingleton() {
		return SCOPE_SIGLETION.equals(this.scope);
	}

	@Override
	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.contentEquals(this.scope);
	}
	@Override
	public String getInitMethodName() {
		return this.initMethod;
	}

	@Override
	public String getDestroyMethodeName() {
		return this.destoryMethodName;
	}

	@Override
	public Class<?> getBeanClass() {
		return beanClass;
	}

	@Override
	public String getScope() {
		return scope;
	}

	@Override
	public String getFactoryBeanName() {
		return factoryBeanName;
	}

	@Override
	public String getFactoryMethodName() {
		return factoryMethodName;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public void setScope(String scope) {
		if (StringUtils.isNotBlank(scope)) {
			this.scope = scope;
		}
	}

	public void setFactoryBeanName(String factoryBeanName) {
		this.factoryBeanName = factoryBeanName;
	}

	public void setFactoryMethodName(String factoryMethodName) {
		this.factoryMethodName = factoryMethodName;
	}

	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod;
	}

	public void setDestoryMethodName(String destoryMethodName) {
		this.destoryMethodName = destoryMethodName;
	}

	public List<Object> getConstructorArgumentValues() {
		return constructorArgumentValues;
	}

	public void setConstructorArgumentValues(List<Object> constructorArgumentValues) {
		this.constructorArgumentValues = constructorArgumentValues;
	}

	public Constructor<?> getConstructor() {
		return constructor;
	}

	public void setConstructor(Constructor<?> constructor) {
		this.constructor = constructor;
	}

	public Method getFactoryMethod() {
		return factoryMethod;
	}

	public void setFactoryMethod(Method factoryMethod) {
		this.factoryMethod = factoryMethod;
	}

	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(List<PropertyValue> propertyValues) {
		this.propertyValues = propertyValues;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (Objects.nonNull(obj)) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		GenericBeanDefinition other = (GenericBeanDefinition) obj;
		if (Objects.isNull(this.beanClass)) {
			if (Objects.nonNull(other.beanClass)) {
				return false;
			}
		} else if (beanClass.equals(other.getBeanClass())) {
			return false;
		}
		
		return true;
	}
	
	public int hasCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (Objects.isNull(beanClass) ? 0 : beanClass.hashCode());
		result = prime * result + (Objects.isNull(scope) ? 0 : scope.hashCode());
		result = prime * result + (Objects.isNull(factoryBeanName) ? 0 : factoryBeanName.hashCode());
		result = prime * result + (Objects.isNull(factoryMethodName) ? 0 : factoryMethodName.hashCode());
		result = prime * result + (Objects.isNull(initMethod) ? 0 : initMethod.hashCode());
		result = prime * result + (Objects.isNull(destoryMethodName) ? 0 : destoryMethodName.hashCode());
		return result;
	}

	@Override
	public Object[] getConstructorArgumentRealValues() {
		return this.constructorArgumentRealValues.get();
	}

	@Override
	public void setConstructorArgumentRealValues(Object[] values) {
		if (Objects.isNull(this.constructorArgumentRealValues)) {
			constructorArgumentRealValues = new ThreadLocal<>();
		}
		constructorArgumentRealValues.set(values);
	}
}
