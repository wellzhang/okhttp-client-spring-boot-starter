package com.wellzhang.okhttp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.text.SimpleDateFormat;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 21:17
 */
public class OkHttpClientContext {

  private static OkHttpClientContext instance;

  private ObjectMapper objectMapper;

  private OkHttpClientHelper okHttpClientHelper;

  public static OkHttpClientContext getInstance() {
    if(instance == null){
      synchronized (OkHttpClientContext.class){
        if(instance == null){
          instance = new OkHttpClientContext();
        }
      }
    }
    return instance;
  }

  public OkHttpClientHelper getOkHttpClientHelper() {
    return okHttpClientHelper;
  }

  public ObjectMapper getObjectMapper(){
    if(objectMapper == null){
      objectMapper = new ObjectMapper();
      // 忽略未知属性
      objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      // 忽略object空值
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
      // 忽略map空值
      objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
      objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    return objectMapper;
  }

  public void setOkHttpClientHelper(OkHttpClientHelper okHttpClientHelper) {
    this.okHttpClientHelper = okHttpClientHelper;
  }

  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
