package com.yld.spring.ioc.springioc.interfaces.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.yld.spring.ioc.springioc.interfaces.beans.impl.PropertyValue;

/**
 * bean定义
 * 告诉bean工厂如果创建bean
 * @author yuleidian
 * @date 2019年3月20日
 */
public interface BeanDefinition {
	
	
	String SCOPE_SIGLETION = "singleton";
	
	String SCOPE_PROTOTYPE = "prototype";
	/**
	 * 获取类
	 * @return
	 */
	Class<?> getBeanClass();
	
	
	/**
	 * 获取创建工厂方法名
	 * @return
	 */
	String getFactoryMethodName();
	
	/**
	 * 获取工厂bean名称
	 * @return
	 */
	String getFactoryBeanName();
	
	/**
	 * 获取作用范围
	 * @return
	 */
	String getScope();
	
	/**
	 * 是否单例
	 * @return
	 */
	boolean isSingleton();
	
	/**
	 * 是否是原型
	 * @return
	 */
	boolean isPrototype();
	
	/**
	 * 获取初始化方法名
	 * @return
	 */
	String getInitMethodName();
	
	/**
	 * 获取销毁方法名
	 * @return
	 */
	String getDestroyMethodeName();
	
	List<Object> getConstructorArgumentValues();

	void setConstructorArgumentValues(List<Object> constructorArgumentValues);

	Constructor<?> getConstructor();

	void setConstructor(Constructor<?> constructor);

	Method getFactoryMethod();
	
	void setFactoryMethod(Method factoryMethod);
	
	List<PropertyValue> getPropertyValues();

	void setPropertyValues(List<PropertyValue> propertyValues);
	
	String getAliasName();

	void setAliasName(String aliasName);
	
	Object[] getConstructorArgumentRealValues();
	
	void setConstructorArgumentRealValues(Object[] values);
	
	
	
	/**
	 * 效验给定信息是否能创建一个bean
	 * @return
	 */
	default boolean validate() {
		if (Objects.isNull(this.getBeanClass())) {
			if (StringUtils.isBlank(this.getFactoryBeanName()) || StringUtils.isBlank(this.getFactoryMethodName())) {
				return false;
			}
			
			if (Objects.nonNull(this.getBeanClass()) && StringUtils.isNotBlank(this.getFactoryBeanName())) {
				return false;
			}
		}
		return true;
	}
}
