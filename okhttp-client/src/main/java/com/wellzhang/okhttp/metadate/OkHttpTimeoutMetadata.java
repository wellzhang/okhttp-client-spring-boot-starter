package com.wellzhang.okhttp.metadate;

/**
 * @author zhangxiang
 * @version 1.0
 * @date 2020/6/21 21:03
 */
public class OkHttpTimeoutMetadata {

  private Integer connectTimeout;
  private Integer readTimeout;
  private Integer writeTimeout;

  public Integer getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(Integer connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public Integer getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(Integer readTimeout) {
    this.readTimeout = readTimeout;
  }

  public Integer getWriteTimeout() {
    return writeTimeout;
  }

  public void setWriteTimeout(Integer writeTimeout) {
    this.writeTimeout = writeTimeout;
  }

  @Override
  public String toString() {
    return "OkHttpTimeoutParam{" +
        "connectTimeout=" + connectTimeout +
        ", readTimeout=" + readTimeout +
        ", writeTimeout=" + writeTimeout +
        '}';
  }
}
