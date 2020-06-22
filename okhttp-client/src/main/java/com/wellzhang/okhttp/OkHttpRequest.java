package com.wellzhang.okhttp;

import java.util.Map;
import org.springframework.util.Assert;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 21:14
 */
public class OkHttpRequest implements HttpRequest{

  private String uri;

  private String desc;

  private Map<String, Object> params;

  public OkHttpRequest(String uri, Map<String, Object> params) {
    this.uri = uri;
    this.params = params;
  }

  @Override
  public String getUri() {
    return this.uri;
  }

  @Override
  public Map<String, Object> getParameters() {
    return this.params;
  }

  @Override
  public Object getParameter(String name) {
    return params.get(name);
  }

  @Override
  public void setParameter(String name, Object value) {
    params.put(name, value);
  }

  @Override
  public void setAllParameters(Map<String, Object> parameters) {
    Assert.notNull(parameters, "parameters is required");
    this.params = parameters;
  }

  @Override
  public void setParameters(Map<String, Object> parameters) {
    this.params.putAll(parameters);
  }

  @Override
  public void setUri(String uri) {
    Assert.notNull(uri);
    this.uri = uri;
  }

  @Override
  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public String getDesc() {
    return this.desc;
  }

}
