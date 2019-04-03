package com.yld.spring.ioc.springioc.interfaces.context.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.yld.spring.ioc.springioc.interfaces.context.Resource;

/**
 * 系统文件源
 * @author yuleidian
 * @date 2019年4月3日
 */
public class FileSystemResource implements Resource {

	private File file;
	
	public FileSystemResource(String path) {
		this.file = new File(path);
	}
	
	public FileSystemResource(File file) {
		super();
		this.file = file;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}

	@Override
	public boolean exists() {
		return Objects.nonNull(file) ? file.exists() : false;
	}

	@Override
	public boolean isReadable() {
		return Objects.nonNull(file) ? file.canRead() : false;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public File getFile() {
		return this.file;
	}

}
