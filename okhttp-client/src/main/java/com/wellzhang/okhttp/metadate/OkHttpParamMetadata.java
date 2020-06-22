package com.wellzhang.okhttp.metadate;

import com.wellzhang.okhttp.annotation.OkHttpParam;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 21:02
 */
public class OkHttpParamMetadata {

  private String name;

  private String defaultValue;

  private Integer index;

  public OkHttpParamMetadata(OkHttpParam okHttpParam, Integer index) {
    this.name = okHttpParam.value();
    this.defaultValue = okHttpParam.defaultValue();
    this.index = index;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }
}
