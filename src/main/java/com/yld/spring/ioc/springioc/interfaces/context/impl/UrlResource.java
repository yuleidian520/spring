package com.yld.spring.ioc.springioc.interfaces.context.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import com.yld.spring.ioc.springioc.interfaces.context.Resource;

public class UrlResource implements Resource {

	private URL url;
	
	public UrlResource(String url) throws IOException {
		this.url = new URL(url);
	}
	
	public UrlResource(URL url) {
		super();
		this.url = url;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (Objects.nonNull(url)) {
			return this.url.openStream();
		}
		return null;
	}

	@Override
	public boolean exists() {
		return Objects.nonNull(url);
	}

	@Override
	public boolean isReadable() {
		return exists() ;
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
