package com.wellzhang.okhttp.spring;

import com.wellzhang.okhttp.annotation.OkHttpClient;
import java.io.IOException;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/16 11:58 下午
 */
public class OkHttpclientDefinitionScanner extends ClassPathBeanDefinitionScanner {

  public OkHttpclientDefinitionScanner(BeanDefinitionRegistry registry) {
    super(registry);
  }

  @Override
  protected void registerDefaultFilters() {
    this.addIncludeFilter(new HttpClientTypeFilter());
  }

  @Override
  protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
    Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

    for (BeanDefinitionHolder holder : beanDefinitions) {
      GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
      definition.getPropertyValues().add("interfaceName", definition.getBeanClassName());
      definition.setBeanClass(OkHttpclientProxyBean.class);
      definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
    }
    return beanDefinitions;
  }

  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return (beanDefinition.getMetadata().isInterface() || beanDefinition.getMetadata().isAbstract()) &&
        beanDefinition.getMetadata().isIndependent();
  }

  static class HttpClientTypeFilter implements TypeFilter {

    @Override
    public boolean match(
        MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
        throws IOException {
      AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
      boolean match = annotationMetadata
          .isAnnotated(OkHttpClient.class.getName());
      return match;
    }

  }

}
