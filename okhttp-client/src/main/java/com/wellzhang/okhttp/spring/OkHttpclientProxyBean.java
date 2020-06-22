package com.wellzhang.okhttp.spring;

import com.wellzhang.okhttp.OkRequestHandler;
import com.wellzhang.okhttp.metadate.OkHttpClientMetadata;
import com.wellzhang.okhttp.metadate.OkHttpRequestMetadata;
import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.core.DefaultNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 21:45
 */
public class OkHttpclientProxyBean<T> implements InitializingBean, FactoryBean<T>,
    ResourceLoaderAware {
  private String interfaceName;

  private Class<?> interfaceClass;

  private ResourceLoader resourceLoader;

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  @Override
  public T getObject() throws Exception {
    return CglibProxy.getInstance(interfaceClass);
  }

  @Override
  public Class<?> getObjectType() {
    return interfaceClass;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.interfaceClass = resourceLoader.getClassLoader().loadClass(this.interfaceName);
  }

  @Override
  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  public final static class CglibProxy implements MethodInterceptor {
    private OkHttpClientMetadata okHttpClientMetadata;

    private CglibProxy(Class<?> innerInterface) {
      this.okHttpClientMetadata = new OkHttpClientMetadata(innerInterface);
    }

    public static <T> T getInstance(Class<?> innerInterface) {
      Enhancer enhancer = new Enhancer();
      enhancer.setSuperclass(innerInterface);
      enhancer.setNamingPolicy(DefaultNamingPolicy.INSTANCE);
      enhancer.setCallback(new CglibProxy(innerInterface));
      return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object arg0, Method method, Object[] arg2, MethodProxy arg3) {
      OkHttpRequestMetadata okHttpRequestMetadata = getRequestMetadata(okHttpClientMetadata, method);
      if(okHttpRequestMetadata != null && StringUtils.isNotEmpty(okHttpRequestMetadata.getPath())){
        OkRequestHandler okRequestHandler = new OkRequestHandler(method, arg2, okHttpRequestMetadata);
        return okRequestHandler.hanlder();
      }
      return null;
    }
  }

  private static OkHttpRequestMetadata getRequestMetadata(OkHttpClientMetadata okHttpClientMetadata, Method method){
    Map<Method, OkHttpRequestMetadata> requestMetadataMap = okHttpClientMetadata.getMethodMappings();
    if (requestMetadataMap != null) {
      return requestMetadataMap.get(method);
    }
    return null;
  }
}
