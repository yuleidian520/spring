package com.yld.spring.ioc.springioc.interfaces.context.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.yld.spring.ioc.springioc.interfaces.context.Resource;

/**
 * 类路径源
 * @author yuleidian
 * @date 2019年4月3日
 */
public class ClassPathResource implements Resource {

	private String path;
	
	private Class<?> clazz;
	
	private ClassLoader loader;
	
	public ClassPathResource(String path) {
		this(path, null);
	}

	public ClassPathResource(String path, Class<?> clazz) {
		this(path, clazz, null);
	}

	public ClassPathResource(String path, Class<?> clazz, ClassLoader loader) {
		super();
		this.path = path;
		this.clazz = clazz;
		this.loader = loader;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public void setLoader(ClassLoader loader) {
		this.loader = loader;
	}

	@Override
	public InputStream getInputStream() {
		if (StringUtils.isNotBlank(path)) {
			if (Objects.nonNull(clazz)) {
				return this.clazz.getResourceAsStream(path);
			}
			
			if (Objects.nonNull(loader)) {
				return this.loader.getResourceAsStream(path.startsWith("/") ? path : path.substring(1));
			}
			return this.clazz.getResourceAsStream(path);
		}
		return null;
	}

	@Override
	public boolean exists() {
		if (StringUtils.isNotBlank(path)) {
			if (Objects.nonNull(clazz)) {
				return Objects.nonNull(this.clazz.getResourceAsStream(path));
			}
			
			if (Objects.nonNull(loader)) {
				return Objects.nonNull(this.loader.getResourceAsStream(path.startsWith("/") ? path : path.substring(1)));
			}
			return Objects.nonNull(this.clazz.getResourceAsStream(path));
		}
		return false;
	}

	@Override
	public boolean isReadable() {
		return exists();
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public File getFile() {
		return null;
	}

}
