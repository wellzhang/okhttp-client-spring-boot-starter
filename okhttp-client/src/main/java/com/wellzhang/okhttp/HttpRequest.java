package com.wellzhang.okhttp;

import java.util.Map;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 20:45
 */
public interface HttpRequest {

  /**
   * 获取uri
   * @return
   */
  public String getUri();

  /**
   * 获取parameters
   * @return
   */
  public Map<String, Object> getParameters();

  /**
   * 获取parameter
   * @param name
   * @return
   */
  Object getParameter(String name);

  /**
   * 设置parameter
   * @param name
   * @param value
   */
  void setParameter(String name, Object value);

  /**
   * 设置全量parameters
   * @param parameters
   */
  void setAllParameters(Map<String, Object> parameters);

  /**
   * 设置parameters
   * @param parameters
   */
  void setParameters(Map<String, Object> parameters);

  /**
   * 设置uri
   * @param uri
   */
  void setUri(String uri);

  /**
   * 设置请求描述
   * @param desc
   */
  void setDesc(String desc);

  /**
   * 获取请求描述
   * @return
   */
  String getDesc();

}
