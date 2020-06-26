package com.wellzhang.okhttp.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellzhang.okhttp.OkHttpClientConfig;
import com.wellzhang.okhttp.OkHttpClientContext;
import com.wellzhang.okhttp.OkHttpClientHelper;
import com.wellzhang.okhttp.utils.SpringContextHolder;
import okhttp3.OkHttpClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author zhangxiang
 * @version 1.0
 * @date 2020/6/21 22:38
 */
public class OkHttpclientBeanProcessor implements ApplicationContextAware, BeanFactoryPostProcessor,
    InitializingBean {

  private String packages;

  private OkHttpClient okHttpClient;

  private OkHttpClientConfig okHttpClientConfig;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContextHolder.setApplicationContext(applicationContext);
  }

  @Override
  public void afterPropertiesSet() {

    ObjectMapper objectMapper = SpringContextHolder.getBeanWithOutException(ObjectMapper.class);
    OkHttpClientContext.getInstance().setObjectMapper(objectMapper);

    if(okHttpClient == null) {
      okHttpClient = SpringContextHolder.getBeanWithOutException(OkHttpClient.class);
    }

    if(okHttpClientConfig == null) {
      okHttpClientConfig = SpringContextHolder.getBeanWithOutException(OkHttpClientConfig.class);
    }
    OkHttpClientHelper okHttpClientHelper = new OkHttpClientHelper();
    okHttpClientHelper.initOkHttpClientHelper(okHttpClient, okHttpClientConfig);
    OkHttpClientContext.getInstance().setOkHttpClientHelper(okHttpClientHelper);
  }

  @Override
  public void postProcessBeanFactory(
      ConfigurableListableBeanFactory beanFactory) throws BeansException {
    Assert.notNull(packages,"未定义扫描路径");
    OkHttpclientDefinitionScanner okHttpclientDefinitionScanner = new OkHttpclientDefinitionScanner((BeanDefinitionRegistry) beanFactory);
    okHttpclientDefinitionScanner.setResourceLoader(SpringContextHolder.getApplicationContext());
    okHttpclientDefinitionScanner.scan(StringUtils.tokenizeToStringArray(packages,
        ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
  }

  public String getPackages() {
    return packages;
  }

  public void setPackages(String packages) {
    this.packages = packages;
  }

  public OkHttpClient getOkHttpClient() {
    return okHttpClient;
  }

  public void setOkHttpClient(OkHttpClient okHttpClient) {
    this.okHttpClient = okHttpClient;
  }

  public OkHttpClientConfig getOkHttpClientConfig() {
    return okHttpClientConfig;
  }

  public void setOkHttpClientConfig(OkHttpClientConfig okHttpClientConfig) {
    this.okHttpClientConfig = okHttpClientConfig;
  }
}