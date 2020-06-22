package com.wellzhang.okhttp;

import java.lang.reflect.Type;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 20:45
 */
public interface HttpResponse {

  /**
   * 获取请求结果字符串
   * @return 结果
   */
  public String getResultString();

  /**
   * 获取http状态码
   * @return String
   */
  public Integer getHttpStatus();

  /**
   * 获取http状态信息
   * @return String
   */
  public String getHttpMessage();

  /**
   * 获取resutType
   * @return type
   */
  public Type getResultType();


  /**
   * 设置resultObject
   * @param resultObject
   */
  public void setResultObject(Object resultObject);

}
