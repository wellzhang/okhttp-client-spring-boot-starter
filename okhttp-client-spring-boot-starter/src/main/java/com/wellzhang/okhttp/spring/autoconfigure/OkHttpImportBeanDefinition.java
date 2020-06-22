package com.wellzhang.okhttp.spring.autoconfigure;

import com.wellzhang.okhttp.spring.OkHttpclientDefinitionScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 23:02
 */
public class OkHttpImportBeanDefinition implements ImportBeanDefinitionRegistrar,
    ResourceLoaderAware {

  private ResourceLoader resourceLoader;

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    AnnotationAttributes attributes = AnnotationAttributes.fromMap(
        importingClassMetadata.getAnnotationAttributes(EnableOkHttpClient.class.getName()));

    String scanPackages = attributes.getString("scanPackages");
    Assert.isTrue(!StringUtils.isEmpty(scanPackages),"未定义扫描路径");

    OkHttpclientDefinitionScanner okHttpclientDefinitionScanner = new OkHttpclientDefinitionScanner(registry);
    if (resourceLoader != null) {
      okHttpclientDefinitionScanner.setResourceLoader(resourceLoader);
    }
    okHttpclientDefinitionScanner.scan(StringUtils.tokenizeToStringArray(scanPackages,
        ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
  }


  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

}
