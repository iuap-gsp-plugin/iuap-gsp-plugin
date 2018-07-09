package com.yonyou.iuap.pap.support.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class SpringContexts implements ApplicationContextAware {

	private static ApplicationContext context;
	
	@Autowired
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringContexts.context = context;
	}
	
	public static <T> T getBean(String beanId, Class<T> clazz) {
		return context.getBean(beanId, clazz);
	}
	
}
