package com.wellzhang.okhttp.annotation;

import com.wellzhang.okhttp.processor.ResponseProcessor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 20:51
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OkBeforeResponse {
  Class<? extends ResponseProcessor>[] value();
}
