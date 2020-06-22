package com.wellzhang.okhttp.spring.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 22:56
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OkHttpImportBeanDefinition.class, OkHttpClientAutoConfiguration.class})
public @interface EnableOkHttpClient {

  /**
   * 扫描包路径
   *
   * @return 路径
   */
  String scanPackages();
}
