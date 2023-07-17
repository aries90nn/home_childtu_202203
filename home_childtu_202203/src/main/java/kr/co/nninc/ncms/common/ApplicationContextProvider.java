package kr.co.nninc.ncms.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Bean이 아닌 클래스에서 Bean객체 필요할 때
 * 
 * @author 나눔
 * @since 2020.12.15
 * @version 1.0
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		applicationContext = ctx;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
		
	}
	
}
