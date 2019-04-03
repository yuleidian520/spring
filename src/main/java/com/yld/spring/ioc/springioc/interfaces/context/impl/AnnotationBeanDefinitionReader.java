package com.yld.spring.ioc.springioc.interfaces.context.impl;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinition;
import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinitionRegistry;
import com.yld.spring.ioc.springioc.interfaces.beans.impl.GenericBeanDefinition;
import com.yld.spring.ioc.springioc.interfaces.context.Resource;
import com.yld.spring.ioc.springioc.interfaces.context.annotations.Autowired;
import com.yld.spring.ioc.springioc.interfaces.context.annotations.Component;
import com.yld.spring.ioc.springioc.interfaces.context.annotations.Qualifier;
import com.yld.spring.ioc.springioc.interfaces.context.annotations.Value;

/**
 * 注解bean定义去读器
 * @author yuleidian
 * @date 2019年4月3日
 */
public class AnnotationBeanDefinitionReader extends AbstractBeanDefinitionReader {

	public AnnotationBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
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
				resolveAndRegisteBeanDefinition(r);
			}
		}
	}

	private void resolveAndRegisteBeanDefinition(Resource resource) {
		if (Objects.nonNull(resource) && Objects.nonNull(resource.getFile())) {
			String className = resolveClassName(resource.getFile());
			
			try {
				Class<?> clazz = Class.forName(className);
				Component component = clazz.getAnnotation(Component.class);
				if (Objects.nonNull(component)) {
					GenericBeanDefinition bd = new GenericBeanDefinition();
					bd.setBeanClass(clazz);
					bd.setScope(component.scopse());
					bd.setFactoryMethodName(component.factoryMethodName());
					bd.setFactoryBeanName(component.factoryBeanName());
					bd.setInitMethod(component.initMethodName());
					bd.setDestoryMethodName(component.destroyMethodName());
					
					resolveConstructor(clazz, bd);
					
					if (StringUtils.isNotBlank(bd.getFactoryMethodName())) {
						resolveFactoryMethodArgs(clazz, bd);
					}
					
					resolvePropertyDI(clazz, bd);
					
					String beanName = StringUtils.isNotBlank(component.value()) ? component.value() : createBeanName(clazz);
					
					this.beanDefinitionRegistry.registerBeanDefinition(beanName, bd);
				}
			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int classPathAbsLength = AnnotationBeanDefinitionReader.class.getResource("/").toString().length();
	
	private String resolveClassName(File file) {
		String absPath = file.getAbsolutePath();
		String name = absPath.substring(classPathAbsLength + 1, absPath.indexOf('.'));
		return StringUtils.replace(name, File.separator, ".");
	}

	private void resolveConstructor(Class<?> clazz, BeanDefinition bd) {
		Constructor<?>[] constructors = clazz.getConstructors();
		if (Objects.nonNull(constructors) && constructors.length > 0) {
			for (Constructor<?> cs : constructors) {
				if (Objects.nonNull(cs.getAnnotation(Autowired.class))) {
					bd.setConstructor(cs);
					Parameter[] ps = cs.getParameters();
					if (Objects.nonNull(ps) && ps.length > 0) {
						resolveConstrutorParameters(ps, bd);
					}
				}
			}
		}
	}
	
	private void resolveConstrutorParameters(Parameter[] ps, BeanDefinition bd) {
		for (Parameter p : ps) {
			if (Objects.nonNull(p.getAnnotation(Value.class))) {
				
			}
			if (Objects.nonNull(p.getAnnotation(Autowired.class))) {
				
			}
			if (Objects.nonNull(p.getAnnotation(Qualifier.class))) {
				
			}
		}
	}

	
	private void resolveFactoryMethodArgs(Class<?> clazz, BeanDefinition bd) {
		// 解析工厂方法参数
	}
	
	private void resolvePropertyDI(Class<?> clazz, BeanDefinition bd) {
		// todo 解析参数依赖
	}
	
	private String createBeanName(Class<?> clazz) {
		String result = null;
		if (Objects.nonNull(clazz)) {
			result = clazz.getName();
			int index = result.lastIndexOf(".") + 1;
			result = result.substring(index);
		}
		return result;
	}
}
