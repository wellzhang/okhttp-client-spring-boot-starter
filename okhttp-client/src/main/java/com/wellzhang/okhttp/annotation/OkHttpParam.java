package com.wellzhang.okhttp.annotation;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 20:51
 */
public @interface OkHttpParam {

  String value() default "";

  String defaultValue() default "";
}
