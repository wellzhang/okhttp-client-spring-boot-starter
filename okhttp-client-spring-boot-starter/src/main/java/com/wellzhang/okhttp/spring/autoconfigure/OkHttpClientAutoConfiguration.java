package com.wellzhang.okhttp.spring.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellzhang.okhttp.OkHttpClientConfig;
import com.wellzhang.okhttp.OkHttpClientContext;
import com.wellzhang.okhttp.OkHttpClientHelper;
import com.wellzhang.okhttp.utils.SpringContextHolder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 23:02
 */
@Configuration
@EnableConfigurationProperties(OkHttpClientConfigProperties.class)
public class OkHttpClientAutoConfiguration implements InitializingBean {

  @Autowired
  private OkHttpClientConfigProperties okHttpClientConfigProperties;

  @Override
  public void afterPropertiesSet() throws Exception {
    ObjectMapper objectMapper = SpringContextHolder.getBeanWithOutException(ObjectMapper.class);
    OkHttpClientContext.getInstance().setObjectMapper(objectMapper);

    OkHttpClient okHttpClient = SpringContextHolder.getBeanWithOutException(OkHttpClient.class);
    OkHttpClientConfig okHttpClientConfig = SpringContextHolder.getBeanWithOutException(OkHttpClientConfig.class);
    if(okHttpClientConfig == null){
      okHttpClientConfig = new OkHttpClientConfig();
      if(okHttpClientConfigProperties != null){
        okHttpClientConfig.setConnectTimeout(okHttpClientConfigProperties.getConnectTimeout());
        okHttpClientConfig.setMaxIdleConnections(okHttpClientConfigProperties.getMaxIdleConnections());
        okHttpClientConfig.setKeepAliveDuration(okHttpClientConfigProperties.getKeepAliveDuration());
        okHttpClientConfig.setMaxTotalConnections(okHttpClientConfigProperties.getMaxTotalConnections());
        okHttpClientConfig.setMaxConnectionsPerRoute(okHttpClientConfigProperties.getMaxConnectionsPerRoute());
        okHttpClientConfig.setReadTimeout(okHttpClientConfigProperties.getReadTimeout());
        okHttpClientConfig.setWriteTimeout(okHttpClientConfigProperties.getWriteTimeout());
      }
    }
    OkHttpClientHelper okHttpClientHelper = new OkHttpClientHelper();
    okHttpClientHelper.initOkHttpClientHelper(okHttpClient, okHttpClientConfig);
    OkHttpClientContext.getInstance().setOkHttpClientHelper(okHttpClientHelper);
  }
}
