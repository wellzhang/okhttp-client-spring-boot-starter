package com.wellzhang.okhttp.enums;

/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/21 20:56
 */
public enum MediaType {

  APPLICATION_JSON("application/json; charset=utf-8"),
  APPLICATION_URLENCODED("application/x-www-form-urlencoded; charset=utf-8");

  private final String value;

  private MediaType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
