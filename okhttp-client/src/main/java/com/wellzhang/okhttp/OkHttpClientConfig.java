package com.wellzhang.okhttp;


/**
 * @author zhangxiang
 * @Description:
 * @Date: 2020/6/16 11:03 下午
 */
public class OkHttpClientConfig {

  /**
   * 最大空闲链接数
   */
  private int maxIdleConnections = 50;

  /**
   * 空闲连接数生存时间 分钟
   */
  private long keepAliveDuration = 5;
  /**
   * 最大连接数
   */
  private int maxTotalConnections = 300;

  /**
   * 增量
   */
  private int maxConnectionsPerRoute = 5;

  /**
   * 链接时长
   */
  private int connectTimeout = 3;

  /**
   * 读时长 秒
   */
  private int readTimeout = 3;

  /**
   * 写时长 秒
   */
  private int writeTimeout = 3;

  public int getMaxIdleConnections() {
    return maxIdleConnections;
  }

  public long getKeepAliveDuration() {
    return keepAliveDuration;
  }

  public int getMaxTotalConnections() {
    return maxTotalConnections;
  }

  public int getMaxConnectionsPerRoute() {
    return maxConnectionsPerRoute;
  }

  public int getConnectTimeout() {
    return connectTimeout;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public int getWriteTimeout() {
    return writeTimeout;
  }

  public void setMaxIdleConnections(int maxIdleConnections) {
    this.maxIdleConnections = maxIdleConnections;
  }

  public void setKeepAliveDuration(long keepAliveDuration) {
    this.keepAliveDuration = keepAliveDuration;
  }

  public void setMaxTotalConnections(int maxTotalConnections) {
    this.maxTotalConnections = maxTotalConnections;
  }

  public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
    this.maxConnectionsPerRoute = maxConnectionsPerRoute;
  }

  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }

  public void setWriteTimeout(int writeTimeout) {
    this.writeTimeout = writeTimeout;
  }

}
