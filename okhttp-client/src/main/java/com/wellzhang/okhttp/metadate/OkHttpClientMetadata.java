package com.wellzhang.okhttp.metadate;

import com.wellzhang.okhttp.annotation.OkHttpClient;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 21:02
 */
public class OkHttpClientMetadata {

  private Class<?> targetClass;

  private String name;

  private Map<Method, OkHttpRequestMetadata> methodMappings;

  public OkHttpClientMetadata(Class<?> targetClass) {
    this.targetClass = targetClass;

    OkHttpClient okHttpClient = targetClass.getAnnotation(OkHttpClient.class);
    if (okHttpClient != null) {
      this.name = okHttpClient.value();
    }
    if (this.name == null || this.name.isEmpty()) {
      String tname = targetClass.getSimpleName();
      this.name = Character.toLowerCase(tname.indexOf(0)) + tname.substring(1);
    }

    Method[] methods = targetClass.getMethods();
    if (methods.length > 0) {
      this.methodMappings = new HashMap<>(methods.length);
    }
    for (Method method : methods) {
      OkHttpRequestMetadata okHttpRequestMetadata = new OkHttpRequestMetadata(method);
      methodMappings.put(method, okHttpRequestMetadata);
    }
  }

  public Class<?> getTargetClass() {
    return targetClass;
  }

  public String getName() {
    return name;
  }

  public Map<Method, OkHttpRequestMetadata> getMethodMappings() {
    return methodMappings;
  }

}
