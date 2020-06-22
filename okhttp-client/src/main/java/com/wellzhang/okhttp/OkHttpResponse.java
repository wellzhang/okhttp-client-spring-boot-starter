package com.wellzhang.okhttp;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author zhangxiang
 * @version 1.0
 * @Description:
 * @date 2020/6/21 21:16
 */
public class OkHttpResponse implements HttpResponse {

  private String resultString;

  private Integer httpStatus;

  private String httpMessage;

  private Object resultObject;

  private Type returnType;

  public OkHttpResponse(okhttp3.Response response) throws IOException {
    this.resultString = response.body().string();
    this.httpStatus = response.code();
    this.httpMessage = response.message();
  }

  @Override
  public String getResultString() {
    return resultString;
  }

  @Override
  public Integer getHttpStatus() {
    return this.httpStatus;
  }

  @Override
  public String getHttpMessage() {
    return this.httpMessage;
  }

  @Override
  public Type getResultType() {
    return this.returnType;
  }

  @Override
  public void setResultObject(Object resultObject) {
    if (resultObject != null) {
      this.resultObject = resultObject;
    }
  }

  public Object getResultObject() {
    return resultObject;
  }

  public Type getReturnType() {
    return returnType;
  }

  public void setReturnType(Type returnType) {
    this.returnType = returnType;
  }

}
