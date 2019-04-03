package com.yld.spring.ioc.springioc.interfaces.context.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinition;

/**
 * 标注类为bean的标记注解
 * @author yuleidian
 * @date 2019年4月3日
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Component {

	
	String value() default "";
	
	String name() default "";
	
	String scopse() default BeanDefinition.SCOPE_SIGLETION;

	String factoryMethodName() default "";

	String factoryBeanName() default "";

	String initMethodName() default "";

	String destroyMethodName() default "";
}
