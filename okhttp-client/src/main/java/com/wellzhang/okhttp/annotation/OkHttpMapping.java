package com.wellzhang.okhttp.annotation;

import com.wellzhang.okhttp.enums.MediaType;
import com.wellzhang.okhttp.enums.RequestMethod;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 20:48
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OkHttpMapping {

  String name() default "";

  String desc() default "";

  String headers() default "";

  String path() default "";

  RequestMethod method() default RequestMethod.GET;

  MediaType produce() default MediaType.APPLICATION_JSON;

}
