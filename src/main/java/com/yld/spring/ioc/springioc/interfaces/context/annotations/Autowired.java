package com.yld.spring.ioc.springioc.interfaces.context.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 自动装配注解
 * @author yuleidian
 * @date 2019年4月3日
 */
@Documented
@Retention(RUNTIME)
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface Autowired {
	
	boolean required() default true;
}
