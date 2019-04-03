package com.yld.spring.ioc.springioc.interfaces.context.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinitionRegistry;
import com.yld.spring.ioc.springioc.interfaces.context.BeanDefinitionReader;
import com.yld.spring.ioc.springioc.interfaces.context.Resource;

/**
 * 解析xml应用上下文
 * @author yuleidian
 * @date 2019年4月3日
 */
public class XmlApplicationContext extends AbstractApplicationContext {

	private List<Resource> resources;
	
	private BeanDefinitionReader beanDefinitionReader;
	
	public XmlApplicationContext(String...location) throws IOException {
		super();
		load(location);
		this.beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) this.beanFactory);
		beanDefinitionReader.loadBeanDefinitions((Resource[])resources.toArray());
	}
	
	@Override
	public Resource getResource(String location) throws IOException {
		if (StringUtils.isNotBlank(location)) {
			if (location.startsWith(Resource.CLASS_PATH_PREFIX)) {
				return new ClassPathResource(location.substring(Resource.CLASS_PATH_PREFIX.length()));
			} else if (location.startsWith(Resource.FILE_SYSTEM_PREFIX)) {
				return new FileSystemResource(location.substring(Resource.FILE_SYSTEM_PREFIX.length()));
			} else {
				return new UrlResource(location);
			}
		}
		return null;
	}

	private void load(String...location) throws IOException {
		if (Objects.isNull(resources)) {
			resources = new ArrayList<Resource>();
		}
		
		if (Objects.nonNull(location) && location.length > 0) {
			for (String l : location) {
				Resource resource = this.getResource(l);
				if (Objects.nonNull(resource)) {
					this.resources.add(resource);
				}
			}
		}
	}
}
