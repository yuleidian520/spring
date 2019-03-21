package com.yld.spring.ioc.springioc.interfaces.impl;

/**
 * 属性依赖
 * @author yuleidian
 * @date 2019年3月21日
 */
public class PropertyValue {

	private String name;
	
	private Object value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
