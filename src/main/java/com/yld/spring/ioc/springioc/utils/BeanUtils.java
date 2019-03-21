package com.yld.spring.ioc.springioc.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import com.yld.spring.ioc.springioc.interfaces.BeanFactory;
import com.yld.spring.ioc.springioc.interfaces.impl.BeanReference;

/**
 * bean工具
 * @author yuleidian
 * @date 2019年3月21日
 */
public abstract class BeanUtils {

	
	public static Object getRealValue(BeanFactory factory, Object obj) {
		Object reuslt = null;
		if (Objects.isNull(obj)) {
			return reuslt;
		} else if (obj instanceof BeanReference) {
			try {
				reuslt = factory.getBean(((BeanReference)obj).getBeanName());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else if (obj instanceof Object[]) {
			
		} else if (obj instanceof Collection) {
			
		} else if (obj instanceof Map) {
			
		} else if (obj instanceof Properties) {
			
		} else {
			reuslt = obj;
		}
		return reuslt;
	}
	
}
