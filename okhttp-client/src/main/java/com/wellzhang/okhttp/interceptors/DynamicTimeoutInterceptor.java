package com.wellzhang.okhttp.interceptors;

import com.wellzhang.okhttp.metadate.OkHttpTimeoutMetadata;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 21:42
 */
public class DynamicTimeoutInterceptor implements Interceptor {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    OkHttpTimeoutMetadata okHttpTimeoutMetadata = RequestTimeoutHolder.get();
    if (okHttpTimeoutMetadata !=null){
      if(logger.isDebugEnabled()) {
        logger.info("[intercept] okHttpTimeoutMetadata:{}.", okHttpTimeoutMetadata);
      }
      if(okHttpTimeoutMetadata.getConnectTimeout() != null && okHttpTimeoutMetadata.getConnectTimeout() > 0){
        chain.withConnectTimeout(okHttpTimeoutMetadata.getConnectTimeout(), TimeUnit.MILLISECONDS);
      }
      if(okHttpTimeoutMetadata.getReadTimeout() != null && okHttpTimeoutMetadata.getReadTimeout() > 0){
        chain.withReadTimeout(okHttpTimeoutMetadata.getReadTimeout(), TimeUnit.MILLISECONDS);
      }
      if(okHttpTimeoutMetadata.getWriteTimeout() != null && okHttpTimeoutMetadata.getWriteTimeout() > 0){
        chain.withWriteTimeout(okHttpTimeoutMetadata.getWriteTimeout(), TimeUnit.MILLISECONDS);
      }
      return chain.proceed(request);
    }
    return chain.proceed(request);
  }
}
