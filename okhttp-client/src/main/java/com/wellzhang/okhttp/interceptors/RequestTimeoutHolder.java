package com.wellzhang.okhttp.interceptors;

import com.wellzhang.okhttp.metadate.OkHttpTimeoutMetadata;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 21:20
 */
public class RequestTimeoutHolder {

  private final static ThreadLocal<OkHttpTimeoutMetadata> timeoutHolder = new ThreadLocal<>();

  public static void remove() {
    timeoutHolder.remove();
  }

  public static void set(OkHttpTimeoutMetadata okHttpTimeoutMetadata) {
    timeoutHolder.set(okHttpTimeoutMetadata);
  }

  public static OkHttpTimeoutMetadata get() {
    return timeoutHolder.get();
  }

}
