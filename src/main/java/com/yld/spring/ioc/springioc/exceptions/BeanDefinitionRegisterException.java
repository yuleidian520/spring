package com.yld.spring.ioc.springioc.exceptions;

/**
 * 123
 * bean定义注册异常
 * @author yuleidian
 * @date 2019年3月20日
 */
public class BeanDefinitionRegisterException extends RuntimeException {

	private static final long serialVersionUID = 2270646985455593415L;

	public BeanDefinitionRegisterException() {}
	
	public BeanDefinitionRegisterException(String message) {
		super(message);
	}
}
