package com.wellzhang.okhttp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 22:34
 */
public class SpringContextHolder implements ApplicationContextAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class);

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContextHolder.applicationContext = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public static <T> T getBean(String name) {
    return (T)applicationContext.getBean(name);
  }

  public static <T> T getBean(Class<T> clz) {
    return applicationContext.getBean(clz);
  }

  public static <T> T getBeanWithOutException(Class<T> clz){
    try {
      return applicationContext.getBean(clz);
    }catch (BeansException e){
      LOGGER.warn("[getBean] class:{}, BeansException:{}.",clz,e.getMessage());
      return null;
    }
  }

  public static <T> T getBeanWithOutException(String name){
    try {
      return (T) applicationContext.getBean(name);
    }catch (BeansException e){
      LOGGER.warn("[getBean] bean name:{}, BeansException:{}.",name,e.getMessage());
      return null;
    }
  }
}
