package com.wellzhang.okhttp.exceptions;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 20:53
 */
public class OkHttpClientException extends RuntimeException {

  public OkHttpClientException() {
    super();
  }

  public OkHttpClientException(String message) {
    super(message);
  }

  public OkHttpClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public OkHttpClientException(Throwable cause) {
    super(cause);
  }

  protected OkHttpClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
