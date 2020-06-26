package com.wellzhang.spring.okhttp.vo.request;

import java.io.Serializable;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/25 10:43
 */
public class TestRequest implements Serializable {

  private static final long serialVersionUID = 7660214966211909695L;

  private String msg;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    return "TestRequest{" +
        "msg='" + msg + '\'' +
        '}';
  }
}
