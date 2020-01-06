package com.cn.niecl.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.PropertiesLoaderSupport;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

public class SpringContextUtil implements ApplicationContextAware {

	private static final String SERVER_NAME = System.getProperty("weblogic.Name");

	/**
	 * The app context.
	 */
	private static ApplicationContext APP_CONTEXT;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public synchronized void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		APP_CONTEXT = applicationContext;
	}

	/**
	 * Gets the bean.
	 * 
	 * @param beanName
	 *            the bean name
	 * @return the bean
	 */
	public static Object getBean(String beanName) {

		if (APP_CONTEXT.containsBean(beanName)) {
			return APP_CONTEXT.getBean(beanName);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: containsBean
	 * @Description: 判断Spring上下文是否存在单个指定Class类型的Bean
	 * @param clzss
	 * @return boolean 返回类型
	 */
	public static boolean containsBean(Class<?> clzss) {
		try {
			APP_CONTEXT.getBean(clzss);
			return true;
		} catch (NoSuchBeanDefinitionException e) {
			return false;
		}
	}

	/**
	 * Gets the bean.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the bean
	 */
	public static <T> T getBean(Class<T> clazz) {
		return APP_CONTEXT.getBean(clazz);
	}

	/**
	 * Gets the bean.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the beans
	 */
	public static <T> List<T> getBeans(Class<T> clazz) {
		Map<String, T> map = APP_CONTEXT.getBeansOfType(clazz);
		return Lists.newArrayList(map.values());
	}

	/**
	 * Gets the context.
	 * 
	 * @return the context
	 */
	public static ApplicationContext getContext() {
		return APP_CONTEXT;
	}

	/**
	 * @Description: 获取通过PropertyPlaceholderConfigurer加载的Properties文件
	 * @return Properties
	 * @throws Exception
	 */
	public static Properties getPropertySource() throws Exception {
		Properties properties = new Properties();
		try {
			String[] postProcessorNames = APP_CONTEXT.getBeanNamesForType(BeanFactoryPostProcessor.class, true, true);
			for (String ppName : postProcessorNames) {
				BeanFactoryPostProcessor beanProcessor = APP_CONTEXT.getBean(ppName, BeanFactoryPostProcessor.class);
				if (beanProcessor instanceof PropertyResourceConfigurer) {
					PropertyResourceConfigurer propertyResourceConfigurer = (PropertyResourceConfigurer) beanProcessor;
					Method mergeProperties = PropertiesLoaderSupport.class.getDeclaredMethod("mergeProperties");
					mergeProperties.setAccessible(true);
					Properties props = (Properties) mergeProperties.invoke(propertyResourceConfigurer);
					Method convertProperties = PropertyResourceConfigurer.class.getDeclaredMethod("convertProperties",
							Properties.class);
					convertProperties.setAccessible(true);
					convertProperties.invoke(propertyResourceConfigurer, props);
					properties.putAll(props);
				}
			}
			return properties;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Checks if is context initialized.
	 * 
	 * @return true, if is context initialized
	 */
	public static boolean isContextInitialized() {
		return APP_CONTEXT != null;
	}

	/**
	 * 获取实例.
	 * 
	 * 如果找不到抛出异常。
	 * 
	 * @param <T>
	 *            the generic type
	 * @param name
	 *            Bean名称
	 * @param type
	 *            Bean类型
	 * @return 实例
	 */
	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name);
		Assert.notNull(type);
		return APP_CONTEXT.getBean(name, type);
	}

	/**
	 * @return the serverName
	 */
	public static String getServerName() {
		return SERVER_NAME;
	}
}
