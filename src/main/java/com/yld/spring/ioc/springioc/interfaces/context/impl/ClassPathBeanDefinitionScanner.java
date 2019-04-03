package com.yld.spring.ioc.springioc.interfaces.context.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.yld.spring.ioc.springioc.interfaces.beans.BeanDefinitionRegistry;
import com.yld.spring.ioc.springioc.interfaces.context.BeanDefinitionReader;
import com.yld.spring.ioc.springioc.interfaces.context.PathMatcher;
import com.yld.spring.ioc.springioc.interfaces.context.Resource;

/**
 * 类路径bean定义扫描器
 * @author yuleidian
 * @date 2019年4月3日
 */
public class ClassPathBeanDefinitionScanner {

	private BeanDefinitionRegistry beanDefinitionRegistry;
	
	private BeanDefinitionReader beanDefinitionReader;
	
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	private String resourcePatter = "**/*.class";
	
	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
		super();
		this.beanDefinitionRegistry = beanDefinitionRegistry;
		this.beanDefinitionReader = new AnnotationBeanDefinitionReader(beanDefinitionRegistry);
	}

	public void scan(String...basePackages) {
		if (Objects.nonNull(basePackages) && basePackages.length > 0) {
			for (String basePackage : basePackages) {
				this.beanDefinitionReader.loadBeanDefinitions(doScan(basePackage));
			}
		}
	}
	
	private Resource[] doScan(String basePackage) {
		String pathPattern = StringUtils.replace(basePackage, ".", "/") + "/" + this.resourcePatter;
		if (pathPattern.charAt(0) != '/') {
			pathPattern = "/" + pathPattern;
		}
		String rootPath = this.determineRootDir(pathPattern);
		String fullPattern = this.getClass().getResource("/").toString() + pathPattern;
		File rootDir = new File(this.getClass().getResource(rootPath).toString());
		Set<Resource> scanedClassFileResources = new HashSet<>();
		this.doRetrieveMatchingFiles(fullPattern, rootDir, scanedClassFileResources);
		return (Resource[]) scanedClassFileResources.toArray();
	}

	
	private void doRetrieveMatchingFiles(String fullPattern, File rootDir, Set<Resource> scanedClassFileResources) {
		for (File content : listDirectory(rootDir)) {
			String currentPath = StringUtils.replace(content.getAbsolutePath(), File.separator, "/");
			if (content.isDirectory() && pathMatcher.match(fullPattern, currentPath + "/")) {
				if (content.canRead()) {
					doRetrieveMatchingFiles(fullPattern, content, scanedClassFileResources);
				} else {
					
				}
				
			}
			if (pathMatcher.match(fullPattern, currentPath)) {
				scanedClassFileResources.add(new FileSystemResource(content));
			}
		}
	}

	private File[] listDirectory(File rootDir) {
		File[] files = rootDir.listFiles();
		if (Objects.isNull(files)) {
			return new File[0];
		}
		Arrays.sort(files, Comparator.comparing(File::getName));
		return files;
	}

	private String determineRootDir(String location) {
		int rootDirEnd = location.length();
		rootDirEnd = location.indexOf('*');
		int zi = location.indexOf('?');
		if (zi != -1 && zi < rootDirEnd) {
			rootDirEnd = location.lastIndexOf('/', zi);
		}
		if (rootDirEnd != -1) {
			return location.substring(0, rootDirEnd);
		}
		return location;
	}

	public BeanDefinitionRegistry getBeanDefinitionRegistry() {
		return beanDefinitionRegistry;
	}

	public void setBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	public BeanDefinitionReader getBeanDefinitionReader() {
		return beanDefinitionReader;
	}

	public void setBeanDefinitionReader(BeanDefinitionReader beanDefinitionReader) {
		this.beanDefinitionReader = beanDefinitionReader;
	}

	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	public String getResourcePatter() {
		return resourcePatter;
	}

	public void setResourcePatter(String resourcePatter) {
		this.resourcePatter = resourcePatter;
	}
}
